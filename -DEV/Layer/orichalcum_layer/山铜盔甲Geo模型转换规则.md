# 山铜盔甲 Geo 模型转换规则

本文档说明如何把 Blockbench 中制作的山铜盔甲模型转换成 GeckoLib 可用的 `orichalcum_armor.geo.json`，并让它在 NeoForge 1.21.1 + GeckoLib 4 中正确渲染。

遇到同类需求（把 Blockbench 盔甲导出为 GeckoLib geo.json）时，可直接套用本文档的规则。

---

## 1. 工程与文件约定

- **Blockbench 工程**：`-DEV/Layer/orichalcum_layer/orichalcum_layer.bbmodel`
- **转换脚本**：`-DEV/Layer/orichalcum_layer/bbmodel_to_geo.py`
- **脚本输出**：`-DEV/Layer/orichalcum_layer/orichalcum_armor.geo.json`
- **游戏资源路径**：`src/main/resources/assets/atlantis_origins/geo/item/orichalcum_armor.geo.json`
- **纹理路径**：`src/main/resources/assets/atlantis_origins/textures/models/armor/orichalcum_layer.png`
- **默认纹理尺寸**：Blockbench 工程 128×128，游戏内纹理 256×256；UV 按 `256 / 128 = 2` 缩放。

## 2. 坐标转换（关键）

**不要**对立方体 `origin` 做 `-to.x`、`24 - to.y`、`-to.z` 等额外镜像。

- **origin**：直接取 Blockbench 的 `from`。
- **size**：直接取 `to - from`。

### 原因

`GeoArmorRenderer.actuallyRender()` 内部会执行：

```java
translate(0, 24 / 16f, 0);
scale(-1, -1, 1);
```

这相当于把模型绕 Z 轴旋转 180°，自动把 Blockbench 坐标映射为 Minecraft 的左右/上下方向。额外翻转会导致：

| 错误操作 | 后果 |
|---|---|
| `origin.y = 24 - to.y` | 盔甲整体上下翻转，头盔掉到脚底 |
| `origin.z = -to.z` | 脑后的 crest 翻到面前 |
| `origin.x = -to.x` | 左右臂位置对调 |

## 3. 骨骼命名与 Pivot

必须使用 GeckoLib 默认盔甲骨骼命名，pivot 固定如下：

| 骨骼名 | Pivot `[x, y, z]` |
|---|---|
| `armorHead` | `[0, 24, 0]` |
| `armorBody` | `[0, 12, 0]` |
| `armorRightArm` | `[5, 22, 0]` |
| `armorLeftArm` | `[-5, 22, 0]` |
| `armorRightLeg` | `[2, 12, 0]` |
| `armorLeftLeg` | `[-2, 12, 0]` |
| `armorRightBoot` | `[2, 12, 0]` |
| `armorLeftBoot` | `[-2, 12, 0]` |

- 子骨骼的 `parent` 直接指向对应盔甲骨骼。
- 子骨骼的 pivot 直接取 Blockbench 组原点，**不要**额外镜像。
- 脚本会自动过滤没有 cube 的空子骨骼。

## 4. 面映射与 UV 旋转

Blockbench 前视图绘制的是 `south` 面，而 Minecraft 盔甲正面是 `north` 面，因此需要互换 `north <-> south`。

| Blockbench 面 | geo.json 面 | UV 旋转 |
|---|---|---|
| `north` | `south` | 不旋转 |
| `south` | `north` | 不旋转 |
| `east` | `east` | **不旋转** |
| `west` | `west` | **不旋转** |
| `up` | `up` | 旋转 180° |
| `down` | `down` | 旋转 180° |

### 注意事项

- `up`/`down` 加 180° 是为了抵消 `scale(-1, -1, 1)` 带来的 Z 轴 180° 旋转。
- `east`/`west` 如果再加 180°，会导致侧面纹理上下、左右同时对调。
- `north`/`south` 互换后 already 正确，不要再加 180°。

## 5. 手臂 UV

当前版本**不再**自动对 `armorLeftArm` / `armorRightArm` 做 U 轴镜像。

如果游戏中手臂纹理左右相反，应优先检查 Blockbench 中的 UV 朝向，而不是在脚本里全局翻转。

## 6. 转换脚本使用流程

```bash
cd ./-DEV/Layer/orichalcum_layer
python3 bbmodel_to_geo.py
cp orichalcum_armor.geo.json ../../../src/main/resources/assets/atlantis_origins/geo/item/orichalcum_armor.geo.json
cd ../../../
./gradlew build
```

脚本会：

1. 读取 `orichalcum_layer.bbmodel`。
2. 按上述规则生成 `orichalcum_armor.geo.json`。
3. 自动过滤空子骨骼。
4. 把 boots 组中 `to.y <= 2.5` 的 cube 划归到 `armorXxxBoot` 骨骼。

## 7. 渲染器配套代码

对应的渲染器为 `OrichalcumArmorRenderer`（`client/render/OrichalcumArmorRenderer.java`）：

- 继承 `GeoArmorRenderer<OrichalcumArmorItem>`。
- 重写 `getRenderType()` 返回 `RenderType.entityTranslucent(texture)`，以支持半透明纹理。
- 如果四肢摆动方向相反，重写 `applyBaseTransformations()`，在 `super.applyBaseTransformations(baseModel)` 之后把四肢骨骼的 `rotX`、`rotY` 再取反一次：

```java
@Override
protected void applyBaseTransformations(HumanoidModel<?> baseModel) {
    super.applyBaseTransformations(baseModel);
    if (this.rightArm != null) {
        this.rightArm.updateRotation(-this.rightArm.getRotX(), -this.rightArm.getRotY(), this.rightArm.getRotZ());
    }
    // ... 对 leftArm / rightLeg / leftLeg / rightBoot / leftBoot 同样处理
}
```

- 如果模型中有需要自发光的子骨骼，可在 `renderRecursively()` 中把对应 bone 的 `packedLight` 设为 `LightTexture.FULL_BRIGHT`；删除该骨骼后应同步移除这段逻辑。

## 8. 常见坑点速查

| 现象 | 原因/解决办法 |
|---|---|
| 盔甲上下颠倒 | 误用了 `24 - to.y` 翻转 origin.y |
| 头盔 crest 翻到面前 | 误用了 `-to.z` 翻转 origin.z |
| 左右臂互换 | 误用了 `-to.x` 翻转 origin.x |
| 侧面纹理上下/左右对调 | 给 `east`/`west` 加了 180° UV 旋转 |
| 前后纹理上下翻转 | 给 `north`/`south` 加了 180° UV 旋转 |
| 四肢摆动方向相反 | 在 `applyBaseTransformations` 中对四肢 `rotX`、`rotY` 再取反一次 |
| 手臂纹理左右相反 | 优先检查 Blockbench UV；当前脚本不再自动镜像手臂 UV |

## 9. 复用清单

把新盔甲从 Blockbench 接入游戏时，按以下顺序检查：

- [ ] Blockbench 组名对应 `armorHead` / `armorBody` / `armorRightArm` / `armorLeftArm` / `armorRightLeg` / `armorLeftLeg` / `armorRightBoot` / `armorLeftBoot`。
- [ ] 立方体 `origin` 直接取 `from`，未做任何镜像。
- [ ] `north`/`south` 已互换，`up`/`down` 已加 180°，`east`/`west` 未加 180°。
- [ ] 运行 `bbmodel_to_geo.py` 并复制 geo.json 到资源目录。
- [ ] 盔甲物品实现 `GeoItem`，`createGeoRenderer` 返回对应 `GeoArmorRenderer`。
- [ ] 渲染器返回 `RenderType.entityTranslucent(texture)`（如需要半透明）。
- [ ] 进游戏检查方向、动画、UV；必要时调整 `applyBaseTransformations` 中的旋转取反。
