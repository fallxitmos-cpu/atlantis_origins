---
name: atlantis-origins
description: Atlantis Origins（亚特兰蒂斯：起源）NeoForge 1.21.1 模组开发上下文与进度汇总。当用户继续开发、修复 bug、添加方块/物品/实体/世界生成/配方/Create 联动内容，或询问本项目相关内容时加载此 skill。
---

# Atlantis Origins 项目上下文

## 1. 项目概况

- **MOD_ID**：`atlantis_origins`
- **当前版本**：`0.2.6-alpha`（见 `gradle.properties`）
- **平台**：NeoForge 1.21.1
- **构建工具**：Gradle wrapper 9.2.1
- **Java**：21
- **主类**：`com.samplecat.atlantisorigins.AtlantisOrigins`
- **工作目录**：`e:\atlantis_origins_1.21.1`

## 2. 依赖关系

- **硬前置**：
  - `curios` `[9.5,10.0)`
  - `create` `[6.0,7.0)`
  - `geckolib` `[4.8.4,)`
- **可选前置**：
  - `patchouli` `[1.21,2.0)`（CLIENT）
- **Create dev 依赖**：通过 CurseMaven `curse.maven:create-328085:7963363` 解析（官方 maven.createmod.net 太慢）。

## 3. 已完成内容摘要（截至 0.2.6-alpha）

### 3.1 流体系统
- 自定义流体/流体类型：锈毒 `rust_poison`（对应流体方块与桶）。液态流珠、纯汞、压缩氧气已删除。
- 客户端流体纹理扩展已注册。
- 自定义咸水/淡水流体与数据组件标记已完全移除，水桶行为恢复为原版（无群系区分）。

### 3.2 矿石与世界生成
- 紫铜矿 `cuprite_ore`、山铜矿 `orichalcum_ore`、硫磺矿 `brimstone_ore`、盐矿 `salt_ore`、银汞齐合金块 `silver_amalgam_block`（掉落银汞齐合金 `silver_amalgam`）。
- 银矿 `silver_ore` + 深层银矿 `deepslate_silver_ore`，任意群系任意高度生成，需钻石镐。
- 山铜矿需下界合金镐，挖掘固定掉落 1 个山铜碎片 `orichalcum_shard`，9 个碎片合成粗山铜 `raw_orichalcum`。

### 3.3 工业材料与 Create 联动
- 紫铜工业材料：板、齿轮、线、管道、机壳、框架、线圈、机壳方块、格栅、管道方块、结构梁、水力管道、能源核心、紫铜小齿轮 `cuprite_cogwheel`、紫铜大齿轮 `cuprite_large_cogwheel`。
- 工业建筑方块：`cuprite_casing_block`、`cuprite_grating`、`cuprite_pipe_block`、`atlantean_mechanical_brick`、`cuprite_beam`、`underwater_glass`、`hydraulic_pipe`、`poseidon_relief`。
- 紫铜压板机 `cuprite_press`（独立实现，不静态引用 Create 方块）。
- 汞蒸馏器 `mercury_distiller`（Create 联动机器）：液态流珠 → 汞碎块 `mercury_shard` + 银粒 `silver_nugget`；4 银粒 → 银锭。
- Create 配方：紫铜矿粉碎、紫铜锭/山铜锭压板、紫铜板切割；紫铜小齿轮/大齿轮合成。
- 新增材料：`vulcanized_rubber`（固化橡胶板）、`cuprite_precision_core`（紫铜精密核心）。

### 3.4 实体
- 深渊水母 `abyssal_jellyfish`（4 色变种：白/金/蓝/红），Y<0 海洋群系生成，掉落深渊触须 `abyssal_tentacle`。
- 发光鱼群 `bioluminescent_fish`，海洋群系任意高度，单次 18~22 条，掉落紫铜粒 `cuprite_nugget`。
- 深海鳗鱼 `deep_eel`、深海蟹 `abyssal_crab`、巨口鱼 `gulper`、磷虾群 `krill_swarm`、刺胞水母 `stinging_jellyfish`、凝视水母 `gazing_jellyfish`。
- 深海守护者 `deep_guardian`：自定义 Blockbench 模型，敌对，Y<30 海洋群系生成，默认手持山铜三叉戟。投掷攻击间隔 40 tick，攻击动画 20 tick，三叉戟在第 14 tick（动画结束前 6 tick）抛出。5% 概率掉落三叉戟。
- 海神利刃 `poseidons_blade`：GeckoLib 实体，继承 `DeepGuardian`，使用 `DefaultedEntityGeoModel` + `GeoEntityRenderer`。血量 300，护甲 17（等效全套钻石甲），仅主动攻击玩家及非敌对生物，不攻击本模组实体与其他海神利刃。
- 利维坦 `leviathan`：仪式召唤 BOSS（开发计划中，尚未实现）。

### 3.5 食物
- 生/熟鳗鱼、蟹肉煲、生/熟巨口鱼肉、磷虾/磷虾酱/磷虾酱面包、深海海藻/海藻卷、盐渍鱼、深海炖菜。

### 3.6 系统与机制
- 氧气、失温、压强、减压病数据附件。
- Curios 槽位：`oxygen_tank`、`goggles`。
- 隐藏状态效果：oppression、overpressure、horror、hypothermia、decompression_sickness、orichalcum_set_bonus。
- 多种机器方块实体与菜单：点金反应炉、注液室、分离塔、催化反应釜、紫铜武器台、汞蒸馏器；研磨台复用 Create `MillstoneBlock`/`MillstoneBlockEntity`。
- 山铜盔甲：已改为 GeckoLib 盔甲模型（`OrichalcumArmorItem implements GeoItem`、`OrichalcumArmorRenderer extends GeoArmorRenderer`、`OrichalcumArmorModel extends DefaultedItemGeoModel`），模型文件为 `geo/item/orichalcum_armor.geo.json`，骨骼使用默认 GeckoLib 盔甲命名。渲染器重写 `getRenderType` 返回 `RenderType.entityTranslucent(texture)`，使 `orichalcum_layer.png` 的半透明像素正确混合；四肢旋转在 `applyBaseTransformations` 中额外取反以修正方向。动画由 `OrichalcumArmorItem` 的 `armor_controller` 循环播放 `成年` 动画。头盔 crest 子骨骼 `bone` 已随模型更新删除，自发光逻辑已移除。
  -  gameplay：每件提供 5% 伤害减免（全套 20%）；靴子额外 +100% 游泳速度、+0.5 水中移动效率（约等于水中阻力减半）；防御值在 Netherite 基础上 +1（头盔 4 / 胸甲 9 / 护腿 7 / 靴子 4），韧性 +1（4.0）。
  - 物品栏纹理已更新头盔/胸甲/护腿，靴子纹理待补。
- 泳镜 `goggles`：装备于 Curios `goggles` 槽时移除水下迷雾；开启光影时额外设置 shader fog 参数作为尽力兼容，但自定义光影的水下雾仍可能生效。
- 海神血肉 `poseidons_flesh`：食用后触发“进化”，需为 `evolved` 数据附件设置 `.copyOnDeath()`，否则死亡重生后进化状态会丢失；建议同时开启 `.sync(...)` 让客户端能读取进化状态。
- 进度：root、deep_diver、abyss_walker、cure_dcs、compressed_miner、survive_horror。

## 4. 待推进内容（按 `development_plan.md`）

- **0.2.1 水下传动**：潮汐发电机、防水轴承、紫铜传动轴。
- **0.2.2 流体机器**：海水过滤器、氧气压缩机、压力泵、汞蒸馏器（✅ 已完成）、汞储罐/盐水晶罐。
- **0.2.3 加工工业化**：机械化点金反应炉、自动化紫铜武器台。
- **0.2.4 工业建筑方块**：紫铜机壳方块、紫铜格栅、紫铜管道方块、亚特兰蒂斯机械砖、能源核心、紫铜结构梁、水下玻璃窗、水力管道、波塞冬浮雕（✅ 已注册方块与配方）。
- **0.2.5 深海生态**：已大体完成实体与食物，后续可补全掉落与行为细节（利维坦仍在计划中）。
- **0.2.6 酿造与药剂**：深海水瓶、深渊之眼、环境叙事物品、形态稳定药剂、痛苦药剂（✅ 已添加物品，具体酿造配方/状态效果待实现）。
- **0.2.7 整合**：JEI/REI、Ponder、多语言、进度、性能优化。

## 5. 关键文件路径

- 开发计划：`-DEV/development_plan.md`
- 更新日志：`CHANGELOG.md`
- 内容注册清单：`-DEV/content_registry.md`
- 构建配置：`build.gradle`、`gradle.properties`
- 依赖声明：`src/main/templates/META-INF/neoforge.mods.toml`
- 主类：`src/main/java/com/samplecat/atlantisorigins/AtlantisOrigins.java`
- 注册类：
  - `common/block/ModBlocks.java`
  - `common/item/ModItems.java`
  - `common/entity/ModEntities.java`
  - `common/fluid/ModFluids.java`、`ModFluidTypes.java`
  - `common/event/SaltwaterBucketHandler.java`（已删除）
  - `common/effect/ModMobEffects.java`
  - `common/damage/ModDamageTypes.java`
  - `common/attachment/ModAttachments.java`
  - `data/ModCreativeTabs.java`
- 客户端注册：`client/ClientSetup.java`
- 数据文件：`src/main/resources/data/atlantis_origins/`
- 资源文件：`src/main/resources/assets/atlantis_origins/`

## 6. 编码约定与注意事项

- **版本规则**（通过 Gradle 任务执行）：
  - 新增方块/物品/实体/机器/酿造等“新元素”后，运行 `./gradlew bumpContentVersion`：
    - `0.2.x-alpha` → `0.2.(x+1)-alpha`，并清空已有的 `-fixN` 后缀。
  - 仅更换纹理、修复 bug、无新元素时，运行 `./gradlew bumpFixVersion`：
    - `0.2.x-alpha` → `0.2.x-alpha-fix1`
    - `0.2.x-alpha-fixN` → `0.2.x-alpha-fix(N+1)`
  - 查看当前版本：`./gradlew showVersion`
- **静态跨模组引用**：禁止在静态初始化器中调用其他模组的 `DeferredHolder.get()` 或 `ofFullCopy(otherModBlock)`。
- **流体注册顺序**：`LiquidBlock`/`BucketItem` 通过 `.get()` 在注册 lambda 中解析；注册顺序保证 FLUID 先于 BLOCK/ITEM。
- **水替换/咸水判定**：自定义咸水/淡水流体与数据组件已完全移除，当前版本无咸水/淡水分化。
- **PNG 安全**：禁止对二进制 PNG 使用文本替换工具。
- **Create 联动已拆分**：主模组不再包含 Create 联动内容。紫铜齿轮、压板机、研磨台、汞蒸馏器及紫铜板/线/管/机壳/框架/线圈/固化橡胶/精密核心等材料已归档到 `-DEV/create_addon/`，未来作为独立模组开发；`mods.toml` 中 Create 已改为可选依赖。
- **原材料创造页**：新增 `itemGroup.atlantis_origins_raw_materials`（“亚特兰蒂斯：起源-原材料”），存放紫铜/银/山铜锭、粗矿、粒、银汞齐合金、硫磺粉、盐、橡胶、炭黑及四种流体桶。
- **Create dev**：开发时 Create 通过 CurseMaven 引入；运行时仍由 `mods.toml` 强制版本。
- **新增内容后**：同步更新 `lang/en_us.json`、`lang/zh_cn.json`，模型/方块状态/战利品/配方/标签。

### 6.5 山铜盔甲 Geo 模型转换规则（`bbmodel_to_geo.py`）

> 完整独立文档见 `-DEV/Layer/orichalcum_layer/山铜盔甲Geo模型转换规则.md`，遇到同类 Blockbench → GeckoLib 盔甲模型需求可直接套用。

山铜盔甲的 GeckoLib 模型从 Blockbench 工程 `-DEV/Layer/orichalcum_layer/orichalcum_layer.bbmodel` 通过脚本 `-DEV/Layer/orichalcum_layer/bbmodel_to_geo.py` 生成，输出到：
- `-DEV/Layer/orichalcum_layer/orichalcum_armor.geo.json`
- `src/main/resources/assets/atlantis_origins/geo/item/orichalcum_armor.geo.json`

#### 坐标转换
- **不要** 对立方体 `origin` 做 `-to.x`、`24 - to.y` 或 `-to.z` 等额外镜像。
- 直接取 Blockbench 的 `from` 作为 `origin`，`to - from` 作为 `size`。
- 原因：`GeoArmorRenderer.actuallyRender()` 会执行 `translate(0, 24/16f, 0); scale(-1, -1, 1)`，等效于绕 Z 轴旋转 180°，它会自动把 Blockbench 坐标映射为 Minecraft 左右/上下方向。额外翻转会导致整体上下或前后颠倒。

#### 骨骼与 Pivot
- 标准盔甲骨骼必须使用默认命名与 pivot：
  - `armorHead` `[0,24,0]`
  - `armorBody` `[0,12,0]`
  - `armorRightArm` `[5,22,0]`
  - `armorLeftArm` `[-5,22,0]`
  - `armorRightLeg` `[2,12,0]`
  - `armorLeftLeg` `[-2,12,0]`
  - `armorRightBoot` `[2,12,0]`
  - `armorLeftBoot` `[-2,12,0]`
- 子骨骼 parent 直接取 Blockbench 组原点，同样不要额外镜像；脚本会自动过滤没有 cube 的空子骨骼。

#### UV 与面映射
- `north <-> south` 互换：Blockbench 前视图绘制的是 `south` 面，而 Minecraft 盔甲正面是 `north` 面。
- `up`、`down` 保持面标签，UV 旋转 `180°`，抵消 renderer 的 Z 轴 180° 旋转。
- `east`、`west` 保持面标签，**不**额外旋转；Blockbench 的 U 轴在 north/south 互换后已与 Minecraft 对齐，再加 `180°` 会让侧面纹理上下左右同时翻转。
- `north`、`south` 不额外加 `180°` UV 旋转。
- 手臂 UV 保持原方向；当前版本不再自动对 `armorLeftArm`/`armorRightArm` 做 U 轴镜像。
- UV 按 `texture_size / bbmodel_resolution.width` 缩放（默认 256/128 = 2）。

#### 运行方式
```bash
cd ./-DEV/Layer/orichalcum_layer
python3 bbmodel_to_geo.py
cp orichalcum_armor.geo.json ../../../src/main/resources/assets/atlantis_origins/geo/item/orichalcum_armor.geo.json
cd ../../../
./gradlew build
```

#### 常见坑点
- 给 `north`/`south` 也加 `uv_rotation: 180` 会导致前后对了但上下翻转。
- 对 `origin.y` 使用 `24 - to.y` 会导致盔甲整体上下翻转、头盔掉到脚底。
- 对 `origin.z` 使用 `-to.z` 会把脑后 crest 翻到面前。
- 对 `origin.x` 使用 `-to.x` 会把左右臂位置对调。
- 如果游戏中四肢运动方向相反，不要改模型坐标，应在 `OrichalcumArmorRenderer` 重写 `applyBaseTransformations`，在 `super.applyBaseTransformations(baseModel)` 之后把四肢骨骼的 `rotX`、`rotY` 再取反一次（`updateRotation(-rotX, -rotY, rotZ)`），因为 Blockbench 导出的局部坐标系与 GeckoLib 默认假设相反。

## 7. 常用命令

```bash
# 编译
./gradlew compileJava

# 完整构建
./gradlew build

# 运行数据生成（如启用）
./gradlew runData
```

## 8. 新会话起手建议

1. 先读取 `-DEV/development_plan.md` 了解版本路线图。
2. 读取 `-DEV/content_registry.md` 核对已有内容，避免重复或 ID 冲突。
3. 查看 `CHANGELOG.md` 了解近期改动。
4. 查看 `gradle.properties` 确认当前版本。
5. 根据用户请求定位到 `common/**/Mod*.java` 或数据文件进行修改。
6. 修改后运行 `./gradlew build` 验证。
