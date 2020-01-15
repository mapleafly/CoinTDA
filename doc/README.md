# CoinTDA
个人虚拟货币交易信息记录本。

## 问题：
1. 你是否购买了n（n > 5）种Coin或Token？
2. 你是否从n(n > 1)个交易所交易？
3. 你是否对某一品种多次交易？
4. 你是否遭遇过交易所交易数据丢失？
5. 鉴于以上某点或者全部，你是否想有一个个人的交易信息记录工具，并且能够帮你统计分析一下综合成本之类的数据？

## 功能：
1. 记录交易信息
2. 查询某品种的交易信息
3. 统计某品种的综合购入成本、个人存量、当前价值
4. 饼图直观显示各品种所占投资比例
5. 导出交易数据(csv格式)

## 特点：
1. 桌面端应用，数据在本地
2. 基础数据采用[coinmarketcap](https://coinmarketcap.com)的数据
3. 开源免费
4. 小巧简单

## 截图：
1. 录入交易信息界面
  ![录入界面](img/2.jpg)

2. 饼图效果
   ![饼图效果](img/4.jpg)

3. 查询、价格分析
   ![价格分析](img/5.jpg)

4. 基础数据选择和查询
   ![基础数据](img/3.jpg)

## 使用：
### 直接使用者：
1. 下载安装文件[setup.exe](https://github.com/mapleafly/CoinTDA/releases/download/V1.1/Setup.exe)到计算机
2. 双击运行setup.exe
3. 按照安装步骤操作，你可以一直“下一步”直到完成安装。
4. 找到安装目录，windows默认安装目录在C:\Program Files (x86)\CoinTDA
5. 准备修改目录下的conf\Cryptocurrency.yml文件
6. 访问[coinmarketcap](https://coinmarketcap.com/api/)
7. 注册一个帐号，申请一个api key
8. 把apikey保存到Cryptocurrency.yml文件

       文件中的第二行apiKey: 默认没有值，把申请的apikey放在这一行的冒号后面
       注意：冒号和你要输入的apikey之间要有一个空格
       
9. 运行cointda.exe
10. 首先更新基础数据

        安装配置完成后，第一次开始使用，首先执行“更新基础数据”
        以后的每次打开软件都可以点击执行“更新基础数据”

11. 在“设置”中点击菜单“选择可用品种”，选择将来要交易的品种并保存。

        因为Coin品种太多，软件默认没有选择可交易品种。初次使用软件，更新基础数据后，
        需要先执行此功能。这样才能在录入界面选择Coin。将来随着交易品种增多，可以在这里
        继续增减。

12. 去数据录入界面输入交易信息。
13. 比例图和分析表查询统计数据。

### 开发者：
本项目开发所用：
1. [AdoptOpenJDK 11.01](https://adoptopenjdk.net/?variant=openjdk11&jvmVariant=hotspot)
2. [openjfx 11.0.2](https://gluonhq.com/products/javafx/)
3. [netbeans 11.2](https://netbeans.apache.org/download/index.html)
4. [maven](https://maven.apache.org/)
5. 其他依赖见[pom.xml](https://github.com/lifxue/CoinTDA/blob/master/pom.xml)