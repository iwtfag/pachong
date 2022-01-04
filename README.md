# pachong
爬取全国疫情数据
[TOC]
# 爬虫项目
爬虫就是在页面上找到同一类自己需要的信息，然后爬取出来存到数据库将来调用，然后在前端显示。我做的是爬取到三个月左右的全国疫情数据，然后在前端可以查询。
## 爬虫的原理
要在Java中实现爬虫功能，首先要在Java后端得到前端页面的数据，这里可以使用jsoup，爬取到前端页面的所有内容，方法如下
1.获取要爬取页面的url
https://voice.baidu.com/newpneumonia/getv2?from=mola-virus&stage=publish&target=trendCity&area=陕西-咸阳
这个页面的截图是这样的

所以要实现这个爬虫要做到下面几步
+ 要把json字符串中的每个元素都提取出来，并把它们合并成一条数据，然后完成上传到数据库
+ 得到一个省市之后，要得到更多省市，所以要改变url最后的地址，然后进行遍历，这样就得到所有城市的数据，并上传到数据库
## 爬虫项目要实现的功能
每天定点爬取数据，在前端页面输入地址和时间，可以获得当天的疫情数据。
## 前端 
前端页面输入地址+日期，得到新增无症状和新增本土两项数据


## 后端
后端先想一下需求
+ 要先爬取一个省份-城市的三个月数据
    + 第一步要获取页面的内容，这里我们要用jsoup插件，它可以把前端页面的代码打印到后端，然后用类似于JQuery的方法来获取页面内容（这里可以看知识树=>一些插件=>jsoup和json）
    + 通过层层解析我们就获得了一个json字符串，我们要把json字符串改为一个json对象，这里需要使用插件fastjson，它可以将json字符串改变json对象，这样我们就能对他进行操作了。
    + 所以要获得能操作的数据，就是后端通过url获取前端页面，然后操作去除无用的数据，最后留下的是有用的数据（json字符串），再对其进行操作，分解，得到想要的数据，下面贴一下代码
url 是 param对象，可以每一步操作都输出一下，看看数据是如何剥离的
```
param = new StringBuffer();
            param.append("https://voice.baidu.com/newpneumonia/getv2?from=mola-virus&stage=publish&target=trendCity&area=").append(pCity);
            System.out.println(param);
            String s = param.toString();

            // TODO: start_time
            Document document = Jsoup.connect(s).ignoreContentType(true).header("Content-Type", "application/json;charset=UTF-8").get();
            // TODO: end_time_1
            Elements elements = document.getElementsByTag("body");
            String text = elements.eq(0).text();
            JSONObject obj = JSON.parseObject(text);//obj是一个json对象


            JSONArray data = (JSONArray) obj.get("data");//data中所有数据
            String msg = (String) obj.get("msg");
            Integer status = (Integer) obj.get("status");

//                System.out.println(data);
            System.out.println("-------------------------------");
            //todo next line data get(0)
            JSONObject realData = (JSONObject) data.get(0);
            System.out.println(realData);

            // 获取城市
            String province_city = (String) realData.get("name");
            // 获取趋势
            JSONObject trend = (JSONObject) realData.get("trend");

            // 获取updateDate
            JSONArray updateDateList = trend.getJSONArray("updateDate");


            //JSONArray noStatusNew = trend.getJSONArray("list");
            JSONArray list = trend.getJSONArray("list");

            JSONObject o = (JSONObject) list.get(0);

            System.out.println("--------------------------");
            // 获取新增无状态:TODO
            JSONArray noStatusList = o.getJSONArray("data");


            System.out.println("++++++++++++++++++++++++++");
            // 获取新增本土:TODO
            JSONObject o1 = (JSONObject) list.get(1);
            JSONArray localNewList = o1.getJSONArray("data");
```
在把数据全部剥离后，最后会变成一条条数组,分别是日期，新增无症状，新增人口。
```
["10.1","10.2","10.3","10.4","10.5","10.6","10.7","10.8","10.9","10.10","10.11","10.12","10.13","10.14","10.15","10.16","10.17","10.18","10.19","10.20","10.21","10.22","10.23","10.24","10.25","10.26","10.27","10.28","10.29","10.30","10.31","11.1","11.2","11.3","11.4","11.5","11.6","11.7","11.8","11.9","11.10","11.11","11.12","11.13","11.14","11.15","11.16","11.17","11.18","11.19","11.20","11.21","11.22","11.23","11.24","11.25","11.26","11.27","11.28","11.29","11.30","12.1","12.2","12.3","12.4","12.5","12.6","12.7","12.8","12.9","12.10","12.11","12.12","12.13","12.14","12.15","12.16","12.17","12.18","12.19","12.20","12.21","12.22","12.23","12.24","12.25","12.26","12.27","12.28","12.29","12.30"]
--------------------------
[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
++++++++++++++++++++++++++
[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,1,1,2,1,3,0,0,2]
```
最后把他们遍历成一条数据
```
for (int i = 0; i < updateDateList.size(); i++) {
    String update_date = (String) updateDateList.get(i);
    int no_status_new = (int) noStatusList.get(i);
    int local_new = (int) localNewList.get(i);
System.out.println(province_city + "," + update_date + "," + no_status_new + "," + local_new);
}
```
这样之后获得的结果是（90多条数据，具体看多少天）
```
陕西-西安，12.12，0，0
。。。
```
这样就获得一个地区的三个月的数据，那我们要获得的是全国所有市的数据，我们就要重复上述的动作n次，即改变url
然后重复操作，而且上面得出的数据还没有保存起来，所以，接下来我们要引入数据库

### 数据库
#### 需求
数据库有三个需求，**第一**把遍历出来的数据存到数据库，**第二**改变url的地址，**第三**要有查询功能，使前端查询时间和地址时会出现相应的数据。
#### 实现
+ 遍历出来的数据存到数据库，写一条insert语句即可
+ url地址改变，即改变最后`陕西-西安`这个位置,那么我们需要建两张表，一张省份，一张城市，然后两表查询，这样就会得到省份-城市这个字符串了，所以我们在网上找一个省份表和城市表的sql语句，稍加修改后复制粘贴
城市                        省份                       

那么查询语句就是`select province.pname,city.cname from province,city where province.pid = city.pid`,然后写相应的jdbc，jdbc方法的返回值可以设一个list，
```
list.add(pName + "-" + cName);
```
这样，list的返回值就是一个“省份-城市”,这样我们就能遍历这个结果代入到url中。

### 优化
这样我们可以爬取，但爬取的速度太慢了，我们需要加入多线程，即现在是一个城市爬完再爬另一个的串行操作速度太慢，我们可以把任务分发到每个城市，让他们自己爬数据。
所以我们可以把穿行操作改为并行操作。
```
public class TestThread extends Thread {
    private String pCity;

    public TestThread(String province_city) {
        this.pCity = province_city;
    }

    @Override
    public void run() {

        ProvinceCityDao dao = new ProvinceCityDao();
        dao.spiderPCityData(this.pCity);

    }
}


```


```
public static void main(String[] args) {

    ProvinceCityDao pDao = new ProvinceCityDao();
    List<String> pList = pDao.getCityList();

    int index = 1;
    while(index < pList.size()){

        TestThread tt = new TestThread(pList.get(index));
        tt.start();
        index++;
    }


```
