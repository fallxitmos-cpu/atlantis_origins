史蒂夫（Steve）玩家模型参考

textures/entity/player/wide/steve.png  - 经典宽型史蒂夫皮肤（64x64）
textures/entity/player/slim/steve.png  - 纤细型史蒂夫皮肤（64x64）

经典宽型 Steve 模型尺寸：
- 头部：8 x 8 x 8
- 躯干：8 x 12 x 4
- 右臂：4 x 12 x 4
- 左臂：4 x 12 x 4
- 右腿：4 x 12 x 4
- 左腿：4 x 12 x 4

纤细型（Slim）Steve 区别：
- 右臂：3 x 12 x 4
- 左臂：3 x 12 x 4

在 Blockbench 中：
1. 新建 "Minecraft Skin" 或 "Generic Model" 项目
2. 导入对应的 steve.png 作为纹理
3. 按上述尺寸建立立方体，UV 可参考原版皮肤布局
   - 头部 UV：顶/底 8x8 区域，侧面 8x8 区域
   - 躯干 UV：顶/底 8x4 区域，侧面 8x12 区域
   - 手臂/腿部 UV：顶/底 4x4 区域，侧面 4x12 区域

建议用宽型 Steve 作为山铜盔甲的建模基础。
