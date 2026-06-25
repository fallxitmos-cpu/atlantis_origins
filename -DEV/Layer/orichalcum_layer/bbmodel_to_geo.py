import json
import pathlib
import sys


def convert(bbmodel_path: pathlib.Path, out_path: pathlib.Path, texture_size: int = 256) -> None:
    bb = json.loads(bbmodel_path.read_text(encoding="utf-8"))

    resolution = bb.get("resolution", {})
    tex_w = resolution.get("width", 128)
    tex_h = resolution.get("height", 128)
    scale_u = texture_size / tex_w
    scale_v = texture_size / tex_h

    by_uuid = {}
    for e in bb.get("elements", []):
        by_uuid[e["uuid"]] = e
    for g in bb.get("groups", []):
        by_uuid[g["uuid"]] = g

    standard_pivots = {
        "armorHead": [0, 24, 0],
        "armorBody": [0, 12, 0],
        "armorLeftArm": [-5, 22, 0],
        "armorRightArm": [5, 22, 0],
        "armorLeftLeg": [-2, 12, 0],
        "armorRightLeg": [2, 12, 0],
        "armorLeftBoot": [-2, 12, 0],
        "armorRightBoot": [2, 12, 0],
    }

    group_to_armor = {
        "armorhead": "armorHead",
        "armorbody": "armorBody",
        "armorleftarm": "armorLeftArm",
        "armorrightarm": "armorRightArm",
        "armorleftleg": "armorLeftLeg",
        "armorrightleg": "armorRightLeg",
    }

    # Blockbench's front view paints the "south" face, while Minecraft's
    # biped front is the "north" face. So we swap north/south. Up/down need a
    # 180° UV rotation to account for GeoArmorRenderer's scale(-1,-1,1).
    # East/west keep their labels and do NOT get a 180° rotation; the Blockbench
    # U axis already aligns with Minecraft's after the north/south swap.
    blockbench_to_mc_face = {
        "north": "south",
        "south": "north",
        "east": "east",
        "west": "west",
        "up": "up",
        "down": "down",
    }

    needs_uv_rotation = {"up", "down"}

    def convert_cube(c):
        f, t = c["from"], c["to"]
        size = [
            round(t[0] - f[0], 4),
            round(t[1] - f[1], 4),
            round(t[2] - f[2], 4),
        ]
        # Keep Blockbench coordinates as-is. GeoArmorRenderer applies
        # scale(-1, -1, 1), which flips X and Y to match Minecraft's biped.
        origin = [round(f[0], 4), round(f[1], 4), round(f[2], 4)]

        uv = {}
        for bb_face, mc_face in blockbench_to_mc_face.items():
            d = c.get("faces", {}).get(bb_face)
            if not d or d.get("texture") is None:
                continue

            u1, v1, u2, v2 = d["uv"]
            entry = {
                "uv": [round(u1 * scale_u, 4), round(v1 * scale_v, 4)],
                "uv_size": [round((u2 - u1) * scale_u, 4), round((v2 - v1) * scale_v, 4)],
            }

            rotation = d.get("rotation", 0)
            if mc_face in needs_uv_rotation:
                rotation = (rotation + 180) % 360
            if rotation:
                entry["uv_rotation"] = rotation

            uv[mc_face] = entry

        cube = {"origin": origin, "size": size, "uv": uv}
        if c.get("inflate"):
            cube["inflate"] = c["inflate"]
        return cube

    bones = {name: {"name": name, "pivot": pivot, "cubes": []} for name, pivot in standard_pivots.items()}
    extra_bones = []

    def is_boot_cube(c):
        return c["to"][1] <= 2.5

    def resolve(ref):
        if isinstance(ref, str):
            return by_uuid.get(ref)
        return by_uuid.get(ref.get("uuid"))

    def append_cube(target_bone, cube):
        target_bone["cubes"].append(cube)

    def make_child_bone(name, parent, pivot):
        bone = {
            "name": name,
            "parent": parent,
            "pivot": [round(pivot[0], 4), round(pivot[1], 4), round(pivot[2], 4)],
            "cubes": [],
        }
        extra_bones.append(bone)
        return bone

    def process_cube_ref(ref, target_bone):
        obj = resolve(ref)
        if obj and obj.get("type") == "cube":
            append_cube(target_bone, convert_cube(obj))

    def process_group_node(node, parent_armor_bone, side_hint=None):
        group = resolve(node)
        if not group:
            return

        gname = group.get("name", "").lower()
        children = node.get("children", []) if isinstance(node, dict) else []

        if gname in ("boot", "boot2"):
            boot_bone = f"armor{side_hint}Boot" if side_hint else parent_armor_bone
            for child in children:
                process_cube_ref(child, bones[boot_bone])
            return

        armor_bone_name = group_to_armor.get(gname)
        if armor_bone_name:
            target = bones[armor_bone_name]
            for child in children:
                child_obj = resolve(child)
                if not child_obj:
                    continue
                if child_obj.get("type") == "cube":
                    append_cube(target, convert_cube(child_obj))
                elif "children" in child_obj:
                    child_gname = child_obj.get("name", "").lower()
                    if child_gname in ("boot", "boot2"):
                        side = "Left" if "Left" in armor_bone_name else "Right"
                        process_group_node(child, armor_bone_name, side_hint=side)
                    else:
                        process_group_node(child, armor_bone_name)
            return

        child_bone = make_child_bone(group.get("name"), parent_armor_bone, group.get("origin", [0, 0, 0]))
        for child in children:
            process_cube_ref(child, child_bone)

    for top in bb.get("outliner", []):
        if not isinstance(top, dict):
            continue
        process_group_node(top, None)

    def clean(b):
        if not b.get("cubes"):
            b.pop("cubes", None)

    bone_list = []
    for name in standard_pivots:
        clean(bones[name])
        bone_list.append(bones[name])
    for eb in extra_bones:
        clean(eb)
        if eb.get("cubes"):
            bone_list.append(eb)

    geo = {
        "format_version": "1.12.0",
        "minecraft:geometry": [{
            "description": {
                "identifier": "geometry.orichalcum_armor",
                "texture_width": texture_size,
                "texture_height": texture_size,
                "visible_bounds_width": 5,
                "visible_bounds_height": 5.5,
                "visible_bounds_offset": [0, 1.5, 0],
            },
            "bones": bone_list,
        }]
    }

    out_path.parent.mkdir(parents=True, exist_ok=True)
    out_path.write_text(json.dumps(geo, indent=2), encoding="utf-8")
    print(f"Wrote {out_path}")


if __name__ == "__main__":
    if len(sys.argv) >= 3:
        convert(pathlib.Path(sys.argv[1]), pathlib.Path(sys.argv[2]))
    else:
        convert(
            pathlib.Path("orichalcum_layer.bbmodel"),
            pathlib.Path("orichalcum_armor.geo.json"),
        )
