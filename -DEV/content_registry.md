# Atlantis Origins 内容注册清单

> 生成时间：2026-06-21  
> 对应版本：`0.2.3-alpha-fix7`  
> MOD_ID：`atlantis_origins`

本文档汇总当前代码与数据包中已注册的全部方块、物品、流体、实体、配方、世界生成、战利品、标签、进度等内容。

---

## 1. 方块（Blocks）

### 1.1 普通方块 / 机器方块

| 注册名 | 说明 | 备注 |
|--------|------|------|
| `atlantis_origins:deep_sea_lamp` | 深海灯 | 亮度 15 |
| `atlantis_origins:diving_light_source` | 潜水光源 | 不可掉落、无碰撞箱 |
| `atlantis_origins:cuprite_ore` | 紫铜矿 | 对应原版铜矿 |
| `atlantis_origins:orichalcum_ore` | 山铜矿 | 需下界合金镐，挖掘固定掉落 1 山铜碎片 |
| `atlantis_origins:brimstone_ore` | 硫磺矿 | 对应原版煤矿 |
| `atlantis_origins:salt_ore` | 盐矿 | 对应原版煤矿 |
| `atlantis_origins:silver_amalgam_block` | 银汞齐合金块 | 掉落银汞齐合金 |
| `atlantis_origins:silver_ore` | 银矿 | 需钻石镐 |
| `atlantis_origins:deepslate_silver_ore` | 深层银矿 | 需钻石镐 |
| `atlantis_origins:cuprite_weapon_station` | 紫铜武器台 | 5×5 工作台，有菜单 |
| `atlantis_origins:alchemical_reactor` | 点金反应炉 | 有 BE/Menu/Recipe |
| `atlantis_origins:liquid_injection_chamber` | 注液室 | 有 BE/Menu/Recipe |
| `atlantis_origins:separation_tower` | 分离塔 | 有 BE/Menu/Recipe |
| `atlantis_origins:catalytic_reactor` | 催化反应釜 | 有 BE/Menu/Recipe；4 原料槽（A~D）+ 2 催化剂槽（E~F）+ 2 燃料槽（G~H，仅烈焰粉/烈焰棒/岩浆）+ 1 输出槽 |
| `atlantis_origins:poseidon_relief` | 波塞冬浮雕 | 装饰方块，生成于遗迹 |

> 注：`grinding_table`、`cuprite_press`、`mercury_distiller`、`cuprite_cogwheel`、`cuprite_large_cogwheel` 及工业建筑方块（`cuprite_casing_block`、`cuprite_grating`、`cuprite_pipe_block`、`atlantean_mechanical_brick`、`cuprite_beam`、`underwater_glass`、`hydraulic_pipe`）已移除或归档到 `-DEV/create_addon/`。

### 1.2 流体方块

| 注册名 | 对应流体 |
|--------|----------|
| `atlantis_origins:crude_quicksilver` | 液态流珠 |
| `atlantis_origins:refined_quicksilver` | 纯汞 |
| `atlantis_origins:compressed_oxygen` | 压缩氧气 |
| `atlantis_origins:rust_poison` | 锈毒 |

---

## 2. 流体（Fluids）与流体类型（FluidType）

### 2.1 流体（Source / Flowing）

| 注册名 | 类型 |
|--------|------|
| `atlantis_origins:crude_quicksilver` | Source |
| `atlantis_origins:flowing_crude_quicksilver` | Flowing |
| `atlantis_origins:refined_quicksilver` | Source |
| `atlantis_origins:flowing_refined_quicksilver` | Flowing |
| `atlantis_origins:compressed_oxygen` | Source |
| `atlantis_origins:flowing_compressed_oxygen` | Flowing |
| `atlantis_origins:rust_poison` | Source |
| `atlantis_origins:flowing_rust_poison` | Flowing |

### 2.2 流体类型

| 注册名 | 密度 | 粘度 | 温度 | 特殊 |
|--------|------|------|------|------|
| `atlantis_origins:crude_quicksilver` | 13500 | 1500 | 320 | 不可游泳 |
| `atlantis_origins:refined_quicksilver` | 13500 | 1200 | 300 | 不可游泳 |
| `atlantis_origins:compressed_oxygen` | 500 | 500 | 280 | 不可溺水 |
| `atlantis_origins:rust_poison` | 1200 | 1500 | 350 | 不可灭火 |

---

## 3. 物品（Items）

### 3.1 装备 / 工具 / 武器

| 注册名 | 说明 |
|--------|------|
| `atlantis_origins:oxygen_tank` | 氧气罐（Curios 槽位） |
| `atlantis_origins:flipper` | 脚蹼 |
| `atlantis_origins:goggles` | 泳镜（Curios 槽位） |
| `atlantis_origins:beginner_diving_suit` | 初学者潜水服 |
| `atlantis_origins:beginner_diving_pants` | 初学者潜水裤 |
| `atlantis_origins:orichalcum_helmet` | 山铜头盔 |
| `atlantis_origins:orichalcum_chestplate` | 山铜胸甲 |
| `atlantis_origins:orichalcum_leggings` | 山铜护腿 |
| `atlantis_origins:orichalcum_boots` | 山铜靴子 |
| `atlantis_origins:orichalcum_pickaxe` | 山铜镐 |
| `atlantis_origins:orichalcum_axe` | 山铜斧 |
| `atlantis_origins:orichalcum_shovel` | 山铜锹 |
| `atlantis_origins:orichalcum_hoe` | 山铜锄 |
| `atlantis_origins:orichalcum_trident` | 山铜三叉戟 |
| `atlantis_origins:hyperbaric_chamber` | 高压气泵 |

### 3.2 原材料 / 工业材料

| 注册名 | 说明 |
|--------|------|
| `atlantis_origins:raw_cuprite` | 粗紫铜 |
| `atlantis_origins:cuprite_ingot` | 紫铜锭 |
| `atlantis_origins:cuprite_nugget` | 紫铜粒 |
| `atlantis_origins:raw_orichalcum` | 粗山铜 |
| `atlantis_origins:orichalcum_shard` | 山铜碎片（9 合 1 粗山铜） |
| `atlantis_origins:orichalcum_ingot` | 山铜锭 |
| `atlantis_origins:raw_silver` | 粗银 |
| `atlantis_origins:silver_nugget` | 银粒 |
| `atlantis_origins:silver_ingot` | 银锭 |
| `atlantis_origins:silver_amalgam` | 银汞齐合金 |
| `atlantis_origins:brimstone_dust` | 硫磺粉 |
| `atlantis_origins:raw_salt` | 粗盐 |
| `atlantis_origins:rubber` | 橡胶 |
| `atlantis_origins:carbon_black` | 炭黑 |
| `atlantis_origins:orichalcum_stick` | 山铜棍 |

> 注：`cuprite_sheet`、`orichalcum_sheet`、`cuprite_gear`、`cuprite_wire`、`cuprite_pipe`、`cuprite_casing`、`cuprite_frame`、`cuprite_coil`、`vulcanized_rubber`、`cuprite_precision_core`、`mercury_shard` 等 Create 联动工业材料已归档到 `-DEV/create_addon/`。

### 3.3 食物

| 注册名 | 饥饿 | 饱和度 | 特殊效果 |
|--------|------|--------|----------|
| `atlantis_origins:raw_eel` | 2 | 0.1 | — |
| `atlantis_origins:cooked_eel` | 5 | 0.4 | — |
| `atlantis_origins:raw_crab_meat` | 2 | 0.2 | — |
| `atlantis_origins:crab_meat_stew` | 7 | 0.36 | —（碗装） |
| `atlantis_origins:raw_gulper_flesh` | 2 | 0.2 | — |
| `atlantis_origins:cooked_gulper` | 6 | 0.33 | 水肺 30s |
| `atlantis_origins:krill` | 2 | 0.1 | — |
| `atlantis_origins:krill_paste` | 3 | 0.3 | — |
| `atlantis_origins:krill_paste_bread` | 7 | 0.36 | 海豚的恩惠 30s |
| `atlantis_origins:deep_sea_kelp` | 1 | 0.1 | — |
| `atlantis_origins:kelp_roll` | 4 | 0.3 | 海豚的恩惠 30s |
| `atlantis_origins:salted_fish` | 6 | 0.42 | — |
| `atlantis_origins:deep_sea_stew` | 8 | 0.375 | 水肺 60s（碗装） |

### 3.4 实体掉落 / 叙事材料

| 注册名 | 说明 |
|--------|------|
| `atlantis_origins:abyssal_tentacle` | 深渊水母掉落 |
| `atlantis_origins:memory_fragment` | 当前无实际掉落来源 |
| `atlantis_origins:pain_crystal` | 刺胞水母掉落 |
| `atlantis_origins:gazing_core` | 凝视水母掉落 |
| `atlantis_origins:fused_scale` | 深海守护者掉落 |
| `atlantis_origins:deep_sea_water_bottle` | 酿造原料 / 叙事材料 |
| `atlantis_origins:abyssal_eye` | 叙事材料（原 Create 联动配方已拆分） |
| `atlantis_origins:broken_log` | 沉船 / 遗迹战利品 |
| `atlantis_origins:temple_inscription` | 海底神殿战利品 |
| `atlantis_origins:recollection_bottle` | 酿造原料 |
| `atlantis_origins:eye_of_revelation` | 用于揭示隐藏结构（原 Create 联动配方已拆分） |
| `atlantis_origins:morph_stable_potion` | 形态稳定药剂 |
| `atlantis_origins:pain_potion` | 痛苦药剂 |

### 3.5 流体桶

| 注册名 |
|--------|
| `atlantis_origins:crude_quicksilver_bucket` |
| `atlantis_origins:refined_quicksilver_bucket` |
| `atlantis_origins:compressed_oxygen_bucket` |
| `atlantis_origins:rust_poison_bucket` |

### 3.6 刷怪蛋

| 注册名 | 对应实体 |
|--------|----------|
| `atlantis_origins:abyssal_jellyfish_spawn_egg` | 深渊水母 |
| `atlantis_origins:bioluminescent_fish_spawn_egg` | 发光鱼群 |
| `atlantis_origins:deep_eel_spawn_egg` | 深海鳗鱼 |
| `atlantis_origins:abyssal_crab_spawn_egg` | 深海蟹 |
| `atlantis_origins:gulper_spawn_egg` | 巨口鱼 |
| `atlantis_origins:krill_swarm_spawn_egg` | 磷虾群 |
| `atlantis_origins:stinging_jellyfish_spawn_egg` | 刺胞水母 |
| `atlantis_origins:gazing_jellyfish_spawn_egg` | 凝视水母 |
| `atlantis_origins:deep_guardian_spawn_egg` | 深海守护者 |

---

## 4. 实体 / 生物（Entities）

| 注册名 | 类型 | 生成条件 | 掉落物 | 备注 |
|--------|------|----------|--------|------|
| `atlantis_origins:abyssal_jellyfish` | 被动 | 海洋群系，Y < 0 | `abyssal_tentacle` | 4 种颜色变种 |
| `atlantis_origins:bioluminescent_fish` | 被动 | 海洋群系任意高度 | `cuprite_nugget` | 单次 18~22 条 |
| `atlantis_origins:deep_eel` | 被动 | 海洋群系，Y < 30 | `raw_eel` | 自定义模型/纹理/动画，发光层 |
| `atlantis_origins:abyssal_crab` | 被动 | 海洋群系 | `raw_crab_meat` | 原版美西螈模型 |
| `atlantis_origins:gulper` | 中立 | 深海群系 | `raw_gulper_flesh` | 原版河豚模型 |
| `atlantis_origins:krill_swarm` | 被动 | 海洋群系 | `krill` | 原版鳕鱼模型 |
| `atlantis_origins:stinging_jellyfish` | 被动 | 冷水/冻洋 | `pain_crystal` | 接触造成混乱 |
| `atlantis_origins:gazing_jellyfish` | 被动 | 深海群系 | `gazing_core` | 凝视触发启示 |
| `atlantis_origins:deep_guardian` | 敌对 | 海洋群系，Y < 30 | `fused_scale` + 5% 山铜三叉戟 | 自定义模型/纹理/动画 |

---

## 5. 方块实体（BlockEntityType）

| 注册名 |
|--------|
| `atlantis_origins:alchemical_reactor` |
| `atlantis_origins:catalytic_reactor` |
| `atlantis_origins:cuprite_press` |
| `atlantis_origins:liquid_injection_chamber` |
| `atlantis_origins:mercury_distiller` |
| `atlantis_origins:separation_tower` |

---

## 6. 菜单（MenuType）

| 注册名 |
|--------|
| `atlantis_origins:alchemical_reactor` |
| `atlantis_origins:catalytic_reactor` |
| `atlantis_origins:cuprite_weapon_station` |
| `atlantis_origins:liquid_injection_chamber` |
| `atlantis_origins:mercury_distiller` |
| `atlantis_origins:separation_tower` |

---

## 7. 配方类型与序列化器

| 配方类型 ID | 序列化器 ID |
|-------------|-------------|
| `alchemical_reactor` | `alchemical_reactor` |
| `catalytic_reactor` | `catalytic_reactor` |
| `cuprite_weapon_station` | `cuprite_weapon_station` |
| `liquid_injection_chamber` | `liquid_injection_chamber` |
| `separation_tower` | `separation_tower` |

---

## 8. 状态效果（MobEffects）

| 注册名 | 类别 | 说明 |
|--------|------|------|
| `atlantis_origins:oppression` | 负面 | 压迫 |
| `atlantis_origins:overpressure` | 负面 | 过压 |
| `atlantis_origins:horror` | 负面 | 恐惧，+10% 游泳速度 |
| `atlantis_origins:hypothermia` | 负面 | 失温 |
| `atlantis_origins:decompression_sickness` | 负面 | 减压病，-30% 移速 |
| `atlantis_origins:orichalcum_set_bonus` | 正面 | 全套山铜盔甲加成 |

---

## 9. 伤害类型（DamageTypes）

| 注册名 | msgId |
|--------|-------|
| `atlantis_origins:true_damage` | `atlantis_origins.true_damage` |
| `atlantis_origins:dcs_damage` | `atlantis_origins.dcs_damage` |

---

## 10. 数据附件（DataAttachments）

| 注册名 | 类型 | 说明 |
|--------|------|------|
| `atlantis_origins:player_oxygen` | Float | 玩家氧气值 |
| `atlantis_origins:hypothermia_ticks` | Integer | 失温累计刻数 |
| `atlantis_origins:pressure_stage` | Integer | 压强阶段 |
| `atlantis_origins:effect_intensity` | Float | 效果强度 |
| `atlantis_origins:dcs_stage` | Integer | 减压病阶段 |
| `atlantis_origins:dcs_timer` | Integer | 减压病计时 |
| `atlantis_origins:dcs_damage_window` | Float | 减压伤害窗口 |
| `atlantis_origins:dcs_pending_mild` | Boolean | 轻度减压病待处理 |
| `atlantis_origins:dcs_last_y` | Double | 上次记录 Y |
| `atlantis_origins:dcs_last_y_tick` | Long | 上次记录 Y 的刻 |
| `atlantis_origins:chunk_fluid_processed` | Boolean | 区块流体替换标记 |

---

## 11. 世界生成（Worldgen）

### 11.1 ConfiguredFeature

| 注册名 |
|--------|
| `atlantis_origins:ore_brimstone` |
| `atlantis_origins:ore_cuprite_small` |
| `atlantis_origins:ore_cuprite_large` |
| `atlantis_origins:ore_orichalcum_small` |
| `atlantis_origins:ore_orichalcum_large` |
| `atlantis_origins:ore_salt` |
| `atlantis_origins:ore_silver` |
| `atlantis_origins:ore_silver_amalgam` |

### 11.2 PlacedFeature

| 注册名 | 备注 |
|--------|------|
| `atlantis_origins:ore_brimstone` | |
| `atlantis_origins:ore_cuprite` | |
| `atlantis_origins:ore_cuprite_large` | |
| `atlantis_origins:ore_orichalcum` | |
| `atlantis_origins:ore_orichalcum_large` | |
| `atlantis_origins:ore_salt` | 仅 `#minecraft:is_ocean` |
| `atlantis_origins:ore_silver` | 任意高度 -64~320 |
| `atlantis_origins:ore_silver_amalgam` | 仅 `#atlantis_origins:quicksilver_biomes` |

---

## 12. 生物群系生成修饰器（BiomeModifier）

### 12.1 矿石

| 文件 | 生物群系 | 特征 |
|------|----------|------|
| `add_ores.json` | `#minecraft:is_overworld` | cuprite、orichalcum、brimstone、silver |
| `add_salt_ore.json` | `#minecraft:is_ocean` | salt |
| `add_silver_amalgam_ore.json` | `#atlantis_origins:quicksilver_biomes` | silver_amalgam_block |

### 12.2 实体

| 文件 | 实体 | 生物群系 | 权重 | 数量 | 特殊限制 |
|------|------|----------|------|------|----------|
| `add_abyssal_jellyfish.json` | 深渊水母 | `#minecraft:is_ocean` | 4 | 1-2 | Y < 0 |
| `add_bioluminescent_fish.json` | 发光鱼群 | `#minecraft:is_ocean` | 14 | 18-22 | 任意高度 |
| `add_deep_eel.json` | 深海鳗鱼 | `#minecraft:is_ocean` | 8 | 2-4 | Y < 30 |
| `add_abyssal_crab.json` | 深海蟹 | `#minecraft:is_ocean` | 6 | 1-2 | |
| `add_gulper.json` | 巨口鱼 | `#minecraft:is_deep_ocean` | 4 | 1-2 | |
| `add_krill_swarm.json` | 磷虾群 | `#minecraft:is_ocean` | 12 | 4-8 | |
| `add_stinging_jellyfish.json` | 刺胞水母 | 冷水/冻洋 | 5 | 1-2 | |
| `add_gazing_jellyfish.json` | 凝视水母 | `#minecraft:is_deep_ocean` | 2 | 1 | |
| `add_deep_guardian.json` | 深海守护者 | `#minecraft:is_ocean` | 3 | 1-2 | Y < 30 |

### 12.3 移除原版水草

| 文件 | 生物群系 | 移除特征 | 步骤 |
|------|----------|----------|------|
| `remove_vanilla_water_plants.json` | `#minecraft:is_ocean` | seagrass_simple、seagrass_mid、seagrass_tall、kelp | vegetal_decoration |

---

## 13. 战利品表（LootTables）

### 13.1 方块

| 方块 | 主要掉落 |
|------|----------|
| `brimstone_ore` | `brimstone_dust` x2（受时运影响） |
| `cuprite_ore` | `raw_cuprite`（2-5，受时运影响） |
| `orichalcum_ore` | `orichalcum_shard` x1（无时运，精准采集得原矿） |
| `silver_amalgam_block` | `silver_amalgam` |
| `salt_ore` | `raw_salt` |
| `silver_ore` / `deepslate_silver_ore` | `raw_silver`（1-4，受时运影响） |
| `alchemical_reactor` / `catalytic_reactor` / `liquid_injection_chamber` / `separation_tower` | 自身 |
| `poseidon_relief` | 自身 |

### 13.2 实体

| 实体 | 掉落物 |
|------|--------|
| `abyssal_jellyfish` | `abyssal_tentacle`（1-2） |
| `bioluminescent_fish` | `cuprite_nugget`（1-3） |
| `deep_eel` | `raw_eel` |
| `abyssal_crab` | `raw_crab_meat` |
| `gulper` | `raw_gulper_flesh` |
| `krill_swarm` | `krill`（1-3） |
| `stinging_jellyfish` | `pain_crystal` |
| `gazing_jellyfish` | `gazing_core` |
| `deep_guardian` | `fused_scale`（1-2）+ 5% 主手 `orichalcum_trident` |

---

## 14. 标签（Tags）

### 14.1 方块标签

| 标签路径 | 包含内容 |
|----------|----------|
| `minecraft:block/mineable/pickaxe` | orichalcum_ore、cuprite_ore、brimstone_ore、salt_ore、silver_amalgam_block、silver_ore、deepslate_silver_ore、liquid_injection_chamber、separation_tower、catalytic_reactor、poseidon_relief |
| `minecraft:block/needs_diamond_tool` | silver_ore、deepslate_silver_ore |
| `minecraft:block/needs_netherite_tool` | orichalcum_ore |
| `minecraft:block/needs_iron_tool` | （当前为空） |
| `atlantis_origins:block/salt_ore_replaceables` | `#minecraft:stone_ore_replaceables`、sand、gravel |

### 14.2 物品标签

| 标签路径 | 包含内容 |
|----------|----------|
| `curios:item/goggles` | `goggles` |
| `curios:item/oxygen_tank` | `oxygen_tank` |
| `c:item/ores/silver` | `silver_ore`、`deepslate_silver_ore` |

### 14.3 流体标签

| 标签路径 | 包含内容 |
|----------|----------|
| `minecraft:fluid/water` | rust_poison、flowing_rust_poison、compressed_oxygen、flowing_compressed_oxygen |

### 14.4 生物群系标签

| 标签路径 | 包含内容 |
|----------|----------|
| `atlantis_origins:worldgen/biome/quicksilver_biomes` | 自定义银汞齐矿生成生物群系 |

---

## 15. 进度（Advancements）

| 注册名 | 标题（中文） | 说明 |
|--------|--------------|------|
| `atlantis_origins:root` | 亚特兰蒂斯：起源 | 开始你的水下旅程 |
| `atlantis_origins:deep_diver` | 深海潜水员 | 游泳时下潜至 Y 20 以下 |
| `atlantis_origins:abyss_walker` | 深渊行者 | 到达海平面以下的深渊 |
| `atlantis_origins:cure_dcs` | 压力治疗 | 使用高压气泵治愈减压病 |
| `atlantis_origins:compressed_miner` | 压缩资源 | 获得压缩空气块 |
| `atlantis_origins:survive_horror` | 深渊恐惧 | 体验最深海的压力 |

---

## 16. Curios 槽位

| 槽位 | 对应物品 |
|------|----------|
| `oxygen_tank` | `oxygen_tank` |
| `goggles` | `goggles` |

---

## 17. 配方列表（Recipe JSON）

| 文件名 | 类型/说明 |
|--------|-----------|
| `alchemical_reactor.json` | 工作台合成 点金反应炉 |
| `beginner_diving_pants.json` | 潜水裤 |
| `beginner_diving_suit.json` | 潜水服 |
| `carbon_black.json` | 炭黑 |
| `catalytic_reactor.json` | 催化反应釜 |
| `cooked_eel_from_smoking.json` | 烟熏：raw_eel → cooked_eel |
| `cooked_gulper_from_smoking.json` | 烟熏：raw_gulper_flesh → cooked_gulper |
| `crab_meat_stew_from_smoking.json` | 烟熏：raw_crab_meat → crab_meat_stew |
| `cuprite_ingot_from_alchemical_reactor.json` | 点金反应炉：粗紫铜 → 紫铜锭 |
| `cuprite_ore_blasting.json` | 高炉：紫铜矿 → 紫铜锭 |
| `cuprite_ore_smelting.json` | 熔炉：紫铜矿 → 紫铜锭 |
| `cuprite_weapon_station.json` | 紫铜武器台 |
| `deep_sea_stew.json` | 深海炖菜 |
| `deep_sea_water_bottle.json` | 深海水瓶 |
| `goggles.json` | 泳镜 |
| `kelp_roll.json` | 海藻卷 |
| `krill_paste.json` | 磷虾酱 |
| `krill_paste_bread.json` | 磷虾酱面包 |
| `liquid_injection_chamber.json` | 注液室 |
| `morph_stable_potion.json` | 形态稳定药剂 |
| `orichalcum_axe.json` / `orichalcum_axe_5x5.json` | 山铜斧（3×3 / 5×5） |
| `orichalcum_boots.json` / `orichalcum_boots_5x5.json` | 山铜靴子 |
| `orichalcum_chestplate.json` / `orichalcum_chestplate_5x5.json` | 山铜胸甲 |
| `orichalcum_helmet.json` / `orichalcum_helmet_5x5.json` | 山铜头盔 |
| `orichalcum_hoe.json` / `orichalcum_hoe_5x5.json` | 山铜锄 |
| `orichalcum_ingot_from_alchemical_reactor.json` | 点金反应炉：粗山铜 → 山铜锭 |
| `orichalcum_leggings.json` / `orichalcum_leggings_5x5.json` | 山铜护腿 |
| `orichalcum_ore_blasting.json` / `orichalcum_ore_smelting.json` | 山铜矿 → 山铜锭 |
| `orichalcum_pickaxe.json` / `orichalcum_pickaxe_5x5.json` | 山铜镐 |
| `orichalcum_shovel.json` / `orichalcum_shovel_5x5.json` | 山铜锹 |
| `orichalcum_stick.json` / `orichalcum_stick_5x5.json` | 山铜棍 |
| `orichalcum_trident.json` / `orichalcum_trident_5x5.json` | 山铜三叉戟 |
| `oxygen_tank.json` | 氧气罐 |
| `pain_potion.json` | 痛苦药剂 |
| `poseidon_relief.json` | 波塞冬浮雕 |
| `raw_cuprite_blasting.json` / `raw_cuprite_smelting.json` | 粗紫铜 → 紫铜锭 |
| `raw_orichalcum_blasting.json` / `raw_orichalcum_smelting.json` | 粗山铜 → 山铜锭 |
| `raw_orichalcum_from_shards.json` | 9 × orichalcum_shard → raw_orichalcum |
| `recollection_bottle.json` | 回忆瓶 |
| `rubber_from_alchemical_reactor.json` | 点金反应炉产橡胶 |
| `salted_fish.json` | 盐渍鱼 |
| `separation_tower.json` | 分离塔 |
| `silver_ingot_from_blasting_raw_silver.json` | 高炉：粗银 → 银锭 |
| `silver_ingot_from_nuggets.json` | 4 × 银粒 → 银锭 |
| `silver_ingot_from_smelting_ore.json` | 熔炉：银矿 → 银锭 |
| `silver_ingot_from_smelting_raw_silver.json` | 熔炉：粗银 → 银锭 |

---

## 18. 新增内容概览（0.2.3-alpha 批次）

### 18.1 已拆分内容

汞蒸馏器（`mercury_distiller`、`MercuryDistillerBlockEntity`、`MercuryDistillerMenu`、`mercury_distilling` 配方类型）以及 Create 联动配方、紫铜工业材料、工业建筑方块已归档到 `-DEV/create_addon/`，未来作为独立 Create 联动模组开发。

### 18.3 流体

新增 `compressed_oxygen`（压缩氧气）与 `rust_poison`（锈毒）两种流体及其桶、方块。

### 18.4 实体

`leviathan`（利维坦）BOSS 尚未实现，目前仅在开发计划中规划。

### 18.5 状态效果

`deep_transformation`、`revelation`、`agony`、`oxygen_compression` 等待在 `ModMobEffects` 中实现。

### 18.6 酿造 / 叙事物品

新增 `deep_sea_water_bottle`、`abyssal_eye`、`broken_log`、`temple_inscription`、`recollection_bottle`、`eye_of_revelation`、`morph_stable_potion`、`pain_potion`。

---

## 19. 待完善 / 占位项

- 深渊水母金/蓝/红变种纹理目前复制白色纹理占位。
- 多种食物、矿石、材料纹理使用原版贴图占位。
- `fr_fr`、`ru_ru`、`ja_jp` 语言文件已同步新增键值，但可能仍有遗漏。
- `memory_fragment` 物品当前无实际掉落来源。
- 深渊水草 `deep_sea_kelp` 仅有物品，无海底植被生成。
- Patchouli 手册 `atlantis_manual` 仅有 book.json 框架。
- 利维坦 BOSS 尚未实现，仅停留在开发计划阶段。
- 压缩氧气 / 锈毒流体效果为占位实现，需后续平衡。
- 液压机尚未实现，仅有开发计划占位。
