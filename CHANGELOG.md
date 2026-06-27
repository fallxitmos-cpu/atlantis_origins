# 更新日志

本文件记录 Atlantis Origins（亚特兰蒂斯：起源）各版本的显著改动。
格式参考 [Keep a Changelog](https://keepachangelog.com/zh-CN/1.0.0/)。

## [Unreleased]

### 新增
- **深海水藻（Deep Sea Kelp）**：
  - 注册方块 `deep_sea_kelp`，占地 1×1，无碰撞体积；纹理暂时复用原版发光浆果。
  - 受随机刻影响，顶部主方块可向上生长，直到上方不再是水源；植株越高生长越慢，倍率按 `0.6 * e^(0.15*(15-x))` 计算（x 为植株高度）。
  - 随机刻会将下方 solid 方块转换为苔藓块，同样受高度倍率影响。
  - 方块现在会含水（`waterlogged`），生成/破坏时保留海水。
  - 结果状态（`fruit=true`）光照等级为 10。
  - 方块状态 `fruit` + 隐藏属性 `abundance`（0–7，实际掉落倍率 +1）控制产量；成熟后丰度为 1，随机刻可继续提升至 8。
  - 右键可收获果实，破坏任意方块会移除整株并掉落被挖方块的产物。
  - 自然生成于 `#minecraft:is_ocean` 群系、Y < 35 的海底地面。
  - 新增群系 **绿藻海域** / **绿藻深海**，水色与沼泽相近，海底以苔藓块、石头、沙砾为主，深海海藻大量生成。
  - 新增对应维度 `atlantis_origins:green_algae_sea` 与 `atlantis_origins:green_algae_deep_sea`（可用命令 `/execute in ...` 进入），分别对应平均海床 Y≈30 与 Y≈10 的平坦海域。
- **荧光种子簇（Glowing Seed Cluster）**：深海水藻果实收获/破坏时掉落，用于加工硅胶。
- **粗制硅胶（Raw Silicone）**：荧光种子簇在熔炉中烧制获得。
- **硅胶（Silicone）**：工作台中 1 粗制硅胶 + 1 硫磺粉 → 2 硅胶。

### 优化
- **深海水藻**：重构为继承原版 `GrowingPlantHeadBlock`/`GrowingPlantBodyBlock`，新增 `deep_sea_kelp_plant` 作为身体段，复用原版生长/连接/更新逻辑，同时保留 `fruit`/`abundance` 自定义状态与整株移除行为。

### Bug 修复（按漏洞与优化审查报告）
- **深海水藻**：`deep_sea_kelp` 头段与 `deep_sea_kelp_plant` 身体段共用 `fruit`/`abundance`/`waterlogged` 属性实例，修复头段向上生长转换为身体段时因属性实例不匹配导致的 `IllegalArgumentException` 崩溃。
- `ModDamageTypes`：移除空的 `DeferredRegister`，统一使用数据包注册表 JSON + `ResourceKey`。
- `OrichalcumArmorEventHandler`：现在会读取 `orichalcumArmorEffectsEnabled` 配置，关闭后不再提供减伤。
- `ModNetwork`：`ToggleRiptidePacket` 服务端 handler 增加持有山铜三叉戟的双重校验。
- `TridentEventHandler`：添加客户端侧校验；暴击率从 80% 降至 **20%**。
- 移除 `PoseidonsAnvilHandler`（其检查的 `"unfinished"` NBT 没有任何物品设置过）。
- `ModAttachments`：为压力/失温/DCS 等相关附件添加 `copyOnDeath()`，避免死亡后状态丢失。
- `ModMobEffects`：注册缺失的 `orichalcum_set_bonus` 状态效果。
- `PotionEventHandler`：改为监听 `MobEffectEvent.Added`，可拦截喷溅/滞留药水、命令等所有来源的水肺效果。
- `OxygenEventHandler`：使用 `player.getRandom()` 替代全局 `Random`。
- `AlchemicalReactorBlockEntity`：`RecipeInput.size()` 改为 4，只包含输入槽。
- 移除 `PressureEventHandler`/`DcsEventHandler`/`HypothermiaEventHandler` 中未使用的 `isAquatic` 方法。
- `ModEntities`：移除生成规则中的不安全类型转换。
- `PoseidonsBlade`：不再调用 `super.registerGoals()` 后立即全部移除；移除重复 import。
- `ClientSetup`：鼠标事件先检查是否持有山铜三叉戟，避免无意义遍历。
- `AdvancementHelper`：为所有现有进度提供 `grantXxx` 方法。
- `ModCreativeTabs`：把 `DEEP_GUARDIAN_TRIDENT` 加入装备创造标签页。
- `ModRecipes`：使用 `RecipeType.simple(...)` 替代手动 `toString()`。
- `AbyssalJellyfish`：`MethodHandle` 初始化/调用失败时回退到 `super.hurt()`，避免类加载崩溃。
- 补全 `zh_cn.json` / `en_us.json` 中 `block.atlantis_origins.diving_light_source` 和 `effect.atlantis_origins.orichalcum_set_bonus` 的翻译。

### 山铜盔甲（Orichalcum Armor）
- 每件山铜盔甲现在提供 **5% 伤害减免**，全套最高 **20%**。
- 山铜靴子额外提供 **+100% 游泳速度** 与 **+0.5 水中移动效率**（约等于水中阻力减半）。
- 护甲基础防御值在 Netherite 基础上 **+1**：头盔 4 / 胸甲 9 / 护腿 7 / 靴子 4。
- 护甲韧性 **+1**：4.0。
- 物品栏纹理已替换：头盔、胸甲、护腿；靴子纹理待补。
- 随模型更新删除了头盔 crest 圆环，渲染器中的 crest 自发光逻辑已移除。

### 深海守护者（Deep Guardian）
- 投掷攻击间隔从 20 tick 调整为 **40 tick**。
- 三叉戟现在在第 **14 tick** 抛出（20 tick 攻击动画结束前 6 tick）。

### 海神利刃（Poseidon's Blade）
- 血量从 60 提升至 **300**。
- 护甲提升至 **17**，等效于全套钻石甲防御。
- 现在**只主动攻击玩家及非敌对生物**；不会主动攻击敌对生物、本模组实体或其他海神利刃。

### 泳镜（Goggles）
- 水下迷雾剔除现在会同时设置 shader fog 参数，对部分读取原版雾参数的光影/Sodium 有更好的兼容性；但自定义光影的水下雾仍可能生效。

### 构建与资源
- 更新了 `-DEV/Layer/orichalcum_layer/bbmodel_to_geo.py`： east/west 面不再做 180° UV 旋转；自动过滤没有 cube 的空子骨骼；移除手臂 UV 左右对调。

## [0.2.6-alpha] - 2026-06-24

### 新增
- 山铜盔甲 GeckoLib 化：半透明渲染、动画循环、四肢旋转修正、侧面 UV 修正。
- 山铜盔甲物品栏纹理（头盔/胸甲/护腿）。

### 修复
- 修复山铜盔甲四肢动画方向相反的问题。
- 修复山铜盔甲侧面纹理上下/左右对调的问题。
- 修复客户端因 `super_resolution` 模组原生库提取失败导致的启动崩溃（非本模组问题，已给出排查方案）。

### 调整
- 删除动态光源方块：山铜头盔不再维持 `DivingLightSourceBlock` 光源。

### 实体与战斗调整
- **深海守卫（Deep Guardian）**：
  - 锁定目标时呼唤半径 32 格内的其他深海守卫共同攻击该目标。
  - 身边 8 格内有 5 个及以上深海守卫时，投掷伤害提高 20%。
  - 完全免疫来自其他深海守卫的伤害。
  - 投掷伤害固定为 **10 点**（由 `TridentEventHandler` 统一处理）。
  - 新增战术 AI：保持 5–9 格理想距离，围绕目标游走，并通过小队冷却错开攻击节奏。
  - 移动速度提高 50%（Guardian 基础 0.5 → 0.75）。
  - 深海守卫三叉戟不再从创造物品栏获取；深海守卫装备该三叉戟的掉落概率改为 0，因此不再掉落。
- **海神利刃（PoseidonsBlade）**：
  - 不再攻击本模组的敌对生物（`Monster`）。
  - 会无差别攻击玩家、中立/被动生物、原版敌对生物以及其他模组的敌对生物。
