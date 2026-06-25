# Atlantis Origins 项目上下文与进度

> 本文件为人工可读的项目快照；Kimi 自动技能请见 `.agents/skills/atlantis-origins/SKILL.md`。
> 生成时间：2026-06-16

## 1. 项目概况

- **MOD_ID**：`atlantis_origins`
- **当前版本**：`0.2.3-alpha-fix13`
- **平台**：NeoForge 1.21.1
- **构建工具**：Gradle wrapper 9.2.1
- **Java**：21
- **主类**：`com.samplecat.atlantisorigins.AtlantisOrigins`

## 2. 依赖关系

- **硬前置**：
  - `curios` `[9.5,10.0)`
  - `create` `[6.0,7.0)`
- **可选前置**：
  - `patchouli` `[1.21,2.0)`（CLIENT）
- **Create dev**：通过 CurseMaven `curse.maven:create-328085:7963363` 解析。

## 3. 已完成内容摘要（截至 0.2.3-alpha）

### 3.1 流体系统
- 四种自定义流体/流体类型：液态流珠 `crude_quicksilver`、纯汞 `refined_quicksilver`、压缩氧气 `compressed_oxygen`、锈毒 `rust_poison`（对应流体方块与桶）。
- 客户端流体纹理扩展已注册。
- 自定义咸水/淡水流体与数据组件标记已完全移除，水桶行为恢复为原版。

### 3.2 矿石与世界生成
- 紫铜矿 `cuprite_ore`、山铜矿 `orichalcum_ore`、硫磺矿 `brimstone_ore`、盐矿 `salt_ore`、流珠矿 `quicksilver_ore`。
- 银矿 `silver_ore` + 深层银矿 `deepslate_silver_ore`，任意群系任意高度生成，需钻石镐。
- 山铜矿需下界合金镐，挖掘固定掉落 1 个山铜碎片 `orichalcum_shard`，9 个碎片合成粗山铜 `raw_orichalcum`。

### 3.3 工业材料（Create 联动已拆分）
- 主模组已移除 Create 联动内容；紫铜齿轮、压板机、研磨台、汞蒸馏器及紫铜板/齿轮/线/管/机壳/框架/线圈/固化橡胶/精密核心等材料已归档到 `-DEV/create_addon/`，未来作为独立模组开发。
- 主模组仅保留基础原材料：紫铜锭/粗紫铜、山铜锭/粗山铜/山铜碎片、银锭/粗银/银粒、银汞齐合金、硫磺粉、盐、橡胶、炭黑。

### 3.4 实体
- 深渊水母 `abyssal_jellyfish`（4 色变种：白/金/蓝/红），Y<0 海洋群系生成，掉落深渊触须 `abyssal_tentacle`。
- 发光鱼群 `bioluminescent_fish`，海洋群系任意高度，单次 18~22 条，掉落紫铜粒 `cuprite_nugget`。
- 深海鳗鱼 `deep_eel`、深海蟹 `abyssal_crab`、巨口鱼 `gulper`、磷虾群 `krill_swarm`、刺胞水母 `stinging_jellyfish`、凝视水母 `gazing_jellyfish`。
- 深海守护者 `deep_guardian`：原版守卫者模型，敌对，Y<30 海洋群系生成，默认手持山铜三叉戟，无限投掷山铜三叉戟，5% 概率掉落。

### 3.5 食物
- 生/熟鳗鱼、蟹肉煲、生/熟巨口鱼肉、磷虾/磷虾酱/磷虾酱面包、深海海藻/海藻卷、盐渍鱼、深海炖菜。

### 3.6 系统与机制
- 氧气、失温、压强、减压病数据附件。
- Curios 槽位：`oxygen_tank`、`goggles`。
- 隐藏状态效果：oppression、overpressure、horror、hypothermia、decompression_sickness、orichalcum_set_bonus。
- 多种机器方块实体与菜单：点金反应炉、注液室、分离塔、催化反应釜、紫铜武器台。

## 4. 待推进内容（按 `development_plan.md`）

- **0.2.1 水下传动**：潮汐发电机、防水轴承、紫铜传动轴。
- **0.2.2 流体机器**：海水过滤器、氧气压缩机、压力泵、汞储罐/盐水晶罐（汞蒸馏器已随 Create 联动拆分至 -DEV/create_addon）。
- **0.2.3 加工工业化**：机械化点金反应炉、自动化紫铜武器台。
- **0.2.4 工业建筑方块**：紫铜机壳方块、紫铜格栅、紫铜管道方块、亚特兰蒂斯机械砖、能源核心、紫铜结构梁、水下玻璃窗、水力管道、波塞冬浮雕。
- **0.2.5 深海生态**：已大体完成实体与食物，后续可补全掉落与行为细节。
- **0.2.6 酿造与药剂**：深海水瓶、深渊之眼、8 种新药水、环境叙事物品。
- **0.2.7 整合**：JEI/REI、Ponder、多语言、进度、性能优化。

## 5. 关键文件路径

- 开发计划：`-DEV/development_plan.md`
- 内容注册清单：`-DEV/content_registry.md`
- 构建配置：`build.gradle`、`gradle.properties`
- 依赖声明：`src/main/templates/META-INF/neoforge.mods.toml`
- 主类：`src/main/java/com/samplecat/atlantisorigins/AtlantisOrigins.java`
- 注册类：
  - `common/block/ModBlocks.java`
  - `common/item/ModItems.java`
  - `common/entity/ModEntities.java`
  - `common/fluid/ModFluids.java`、`ModFluidTypes.java`
  - `common/component/ModDataComponents.java`、`common/event/SaltwaterBucketHandler.java`（已删除）
  - `common/effect/ModMobEffects.java`
  - `common/damage/ModDamageTypes.java`
  - `common/attachment/ModAttachments.java`
  - `data/ModCreativeTabs.java`
- 客户端注册：`client/ClientSetup.java`
- 数据文件：`src/main/resources/data/atlantis_origins/`
- 资源文件：`src/main/resources/assets/atlantis_origins/`

## 6. 编码约定与注意事项

- **版本规则**：添加内容 → 增加 PATCH 并重置 fix 计数；bug 修复 → 增加 fix 计数。
- **静态跨模组引用**：禁止在静态初始化器中调用其他模组的 `DeferredHolder.get()` 或 `ofFullCopy(otherModBlock)`。
- **流体注册顺序**：`LiquidBlock`/`BucketItem` 通过 `.get()` 在注册 lambda 中解析；注册顺序保证 FLUID 先于 BLOCK/ITEM。
- **水替换/咸水判定**：自定义咸水/淡水流体与数据组件已完全移除，当前版本无咸水/淡水分化。
- **PNG 安全**：禁止对二进制 PNG 使用文本替换工具。
- **Create dev**：Create 联动内容已拆分；主模组 `mods.toml` 中 Create 改为可选依赖。
- **新增内容后**：同步更新 `lang/en_us.json`、`lang/zh_cn.json`，模型/方块状态/战利品/配方/标签。

## 7. 常用命令

```bash
./gradlew compileJava
./gradlew build
./gradlew runData
```

## 8. 新会话起手建议

1. 先读取 `-DEV/development_plan.md` 了解版本路线图。
2. 读取 `-DEV/content_registry.md` 核对已有内容，避免重复或 ID 冲突。
3. 查看 `gradle.properties` 确认当前版本。
4. 根据用户请求定位到 `common/**/Mod*.java` 或数据文件进行修改。
5. 修改后运行 `./gradlew build` 验证。
