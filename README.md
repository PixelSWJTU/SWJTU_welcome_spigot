# SWJTU_welcome_spigot

西南交通大学mc迎新项目

## 已经实现的功能

1. 实现了部分动画的监听以及播放，实现了材质包的分发

2. ~~实现了多命令执行~~(在实际中用的少)

3. 实现了自动铺路

4. 实现了对csv文件的点位图进行导入

5. 实现了两点快捷连线

## TODO

1. 实现对GIS线段的解析（由于有另一个技术路线，~~其实可以不用实现~~



## Usage

### 两点快速连线

首先通过 `/linematerial line GRASS_BLOCK` 设置连线的材质为`GRASS_BLOCK`或其他需要的材质

之后手持`石斧头`，左键第一个点，右键第二个点即可完成连线。



### 导入csv文件

首先将csv文件上传至服务器，且保证csv文件只有两列`x,y`, 之后通过`blocksetter SMOOTH_STONE_SLAB <world> <filename> <Y>`

请保证`filename`为绝对路径



### 快速铺路

首先通过`/linematerial line GRASS_BLOCK` 设置道路的材质

再通过 `/roadswitch line <double|single|off> ` 设置是否需要中线，默认关闭

再使用 `/roadswitch switch <off|on>` 开启或者关闭自动铺路

之后边走边铺路咯



自动铺路将检测玩家左右两侧40格的范围内的`平滑石头台阶`，并将在两个石头台阶之间放置道路，所以请尽量**在路的中间行驶**
