package com.dragon.patternframework.commodity.support;

/**
 * Created by 40774 on 2017/12/5.
 */

public class TestDataSupport {
    //    定义商品出卖方式
    //   数据暂存
//    private List<Commodity> products = new ArrayList<>();
//    private static final TestDataSupport ourInstance = new TestDataSupport();
//
//    public static TestDataSupport getInstance() {
//        return ourInstance;
//    }
//
//    private TestDataSupport() {
//    }
//
//    public List<Commodity> getTestAD() {
//        //        伪代码 网络获取
//        final List<Commodity> products = new ArrayList<>();
////    价格等等先不设置
//            Commodity product0 = new Commodity();
//            product0.setImages("http://39.108.123.220/ADimageviewurl/小风扇.jpg");
//            product0.setNote("灵巧、方便携带、可爱");
//            product0.setId(12);
//            products.add(product0);
//            Commodity product1 = new Commodity();
//            product1.setImages("http://39.108.123.220/ADimageviewurl/山地自行车.jpg");
//            product1.setNote("性能卓越，亮色外观");
//            product1.setId(11);
//            products.add(product1);
//            Commodity  Commodity2 = new  Commodity();
//            Commodity2.setImages("http://39.108.123.220/ADimageviewurl/基督山伯爵.jpg");
//            Commodity2.setNote("基督山伯爵，走进文学。");
//            Commodity2.setId(4);
//            products.add( Commodity2);
//            Commodity  Commodity3 = new  Commodity();
//            Commodity3.setImages("http://39.108.123.220/ADimageviewurl/牦牛肉.jpg");
//            Commodity3.setNote("美味而筋道，良品铺子");
//            Commodity3.setId(10);
//            products.add( Commodity3);
//            Commodity  Commodity4 = new  Commodity();
//            Commodity4.setImages("http://39.108.123.220/ADimageviewurl/抽纸.jpg");
//            Commodity4.setNote("一阵清风拂过");
//            Commodity4.setId(13);
//            products.add( Commodity4);
//        return products;
//    }
//
//    public List<Commodity> getTestProduct() {
//        //        伪代码 网络获取
//        final List<Commodity> products = new ArrayList<>();
//        products.add(setProduct(1, getListTabname(TabName.FOOD, TabName.SELECT), "火龙果", "火龙果营养丰富、功能独特，它含有一般植物少有的植物性白蛋白以及花青素，丰富的维生素和水溶性膳食纤维。火龙果属于凉性水果，在自然状态下，果实于夏秋成熟，味甜，多汁。"
//                , "不知名的小果", 26.00f, 1));
//        products.add(setProduct(2, getListTabname(TabName.FOOD), "哈密瓜", "哈密瓜（Cucumis melo var. saccharinus），是甜瓜的一个转变。又名雪瓜、贡瓜，是一类优良甜瓜品种，果型圆形或卵圆形，出产于新疆。味甜，果实大，以哈密所产最为著名，故称为哈密瓜。",
//                "不知名的小果", 34.00f, 1));
//        products.add(setProduct(3, getListTabname(TabName.FOOD, TabName.AFFORDABLE), "橘子", "橘子味甘酸、性温，入肺。主要治胸隔结气、呕逆少食、胃阴不足、口中干渴、肺热咳嗽及饮酒过度。具有开胃、止渴润肺的功效。橘子营养也十分丰富，一个橘子就几乎满足人体一天中所需的维生素C含量。并且橘子中含有170余种植物化合物和60余种黄酮类化合物，其中的大多数物质均是天然抗氧化剂。",
//                "不知名的小果", 2.50f, 1));
//        products.add(setProduct(4, getListTabname(TabName.LATEST, TabName.SELECT), "基督山伯爵", "《基督山伯爵》是通俗历史小说，法国著名作家大仲马（1802-1870）的代表作。故事讲述19世纪法国皇帝拿破仑“百日王朝”时期，法老号大副爱德蒙·邓蒂斯受船长委托，为拿破仑党人送了一封信，遭到两个卑鄙小人和法官的陷害，被打入黑牢。狱友法利亚神甫向他传授各种知识，并在临终前把埋于基督山岛上的一批宝藏的秘密告诉了他。唐泰斯越狱后找到了宝藏，成为巨富，从此化名基督山伯爵（水手森巴），经过精心策划，报答了恩人，惩罚了仇人。充满传奇色彩，奇特新颖，引人入胜。",
//                "李某某", 92.80f, 1));
//        products.add(setProduct(5, getListTabname(TabName.FOOD, TabName.SELECT), "坚果", "果仁香酥滑嫩可口，有独特的奶油香味，是世界上品质较佳的食用坚果，素有“干果皇后”“世界坚果之王”之美称 ，风味和口感都远比腰果好",
//                "良品铺子", 23.00f, 1));
//        products.add(setProduct(6, getListTabname(TabName.NOVEL, TabName.LATEST), "尖叫鸡", "搞怪、整蛊朋友，快乐无处不在。",
//                "旅人", 6.90f, 1));
//        products.add(setProduct(7, getListTabname(TabName.SELECT, TabName.NOVEL, TabName.DAILY), "发光小风扇", "创意小工具，可用于表白，吹吹风。一丝清凉，沁人心脾~",
//                "不明真相的同学", 10.00f, 1));
//        products.add(setProduct(8, getListTabname(TabName.DAILY, TabName.AFFORDABLE), "钢笔", "玫红与青绿的色块在黑底上交融错杂，不同角度下能看见炫目的反光，百乐家经典的74造型",
//                "文具先生", 79.00f,1));
//        products.add(setProduct(9, getListTabname(TabName.LATEST, TabName.DAILY), "变色杯", "在杯子里注入热水或冷水，杯体将会发生颜色变化，在没有变色之前的颜色有黑色，红色，黄色，绿色，蓝色，褐色，紫色，浅蓝，浅黄，浅绿，淡红，淡紫，粉色等。",
//                "不明真相的同学", 21.00f, 1));
//        products.add(setProduct(10, getListTabname(TabName.FOOD, TabName.SELECT), "牦牛肉", "牦牛肉是一道菜品，制作原料主要有牦牛臀尖肉、豆油等。牦牛肉质细嫩，味道鲜美。具有极高的营养价值极高。富含蛋白质和氨基酸，以及胡萝卜素、钙、磷等微量元素，脂肪含量特别低，热量特别高，对增强人体抗病力、细胞活力和器官功能均有显著作用。",
//                "良品铺子", 59.00f, 1));
//        products.add(setProduct(11, getListTabname(TabName.LARGE_ITEM, TabName.AFFORDABLE), "山地自行车", "山地车是专门为越野（丘陵，小径，原野及砂土碎石道等）行走而设计的自行车，一九七七年诞生于美国西岸的旧金山。当时，一群热衷于骑沙滩自行车在山坡上玩乐的年轻人，突发奇想：“要是能骑着自行车从山上飞驰而下，一定非常有趣。”",
//                "李某某", 586.00f,1));
//        products.add(setProduct(12, getListTabname(TabName.SELECT, TabName.NOVEL), "小风扇", "USB风扇是应用在台式电子计算机或是笔记本电脑的USB接口的微型电风扇。新型的无刷直流电机风扇比起传统的马达风扇更安静、更省电、寿命更长。",
//                "旅人", 35.50f, 1));
//        products.add(setProduct(13, getListTabname(TabName.LATEST, TabName.DAILY), "抽纸", "100%纯木浆，不添加任何化学漂白剂，纸的形状一般为方形对折，抽第一张后带出第二张。",
//                "不明真相的同学", 4.00f, 1));
//        products.add(setProduct(14, getListTabname(TabName.DAILY), "电脑耳机", "游戏、音乐、视频，它无处不在。",
//                "不明真相的同学", 32.00f, 1));
//        products.add(setProduct(15, getListTabname(TabName.SELECT, TabName.DAILY, TabName.AFFORDABLE), "暖手宝", "内部发热片发热，使用USB接口充放电，跟暖手鼠标垫类似，由于锂电池能充放电的功能，一般也能做为移动电源使用。体积小巧，方便随身携带，温度能达到50℃左右。",
//                "旅人", 20.00f,1));
////             伪代码添加到缓冲数据
//        this.products.addAll(products);
//        return products;
//    }
//
//    private Commodity setProduct(int id, List<TabName> tabName, String name, String describe, String user, float price, int IsAmount) {
//        Commodity product = new Commodity();
//            product.setImages("http://39.108.123.220/ADimageviewurl/" + name + ".jpg");
//            product.setNote(describe);
//            product.setComLabel(name);
//            product.setUseId(1025);
//            product.setPrice(price);
//            product.setAmount(IsAmount);
//            for (TabName tabName1:TabName.values())
//            product.setTab(tabName1.ordinal()+"");
//            product.setId(id);
//        return product;
//    }
//
//    public Commodity getProduct(int id) {
//        for (Commodity product : products) {
//            if (product.getId() == id)
//                return product;
//        }
//        return null;
//    }
//
//    private List<TabName> getListTabname(TabName tabName0) {
//        List<TabName> tabNames = new ArrayList<>();
//        tabNames.add(tabName0);
//        return tabNames;
//    }
//
//    private List<TabName> getListTabname(TabName tabName0, TabName tabName1) {
////        重载
//        List<TabName> tabNames = new ArrayList<>();
//        tabNames.add(tabName0);
//        tabNames.add(tabName1);
//        return tabNames;
//    }
//
//    private List<TabName> getListTabname(TabName tabName0, TabName tabName1, TabName tabName2) {
////        重载
//        List<TabName> tabNames = new ArrayList<>();
//        tabNames.add(tabName0);
//        tabNames.add(tabName1);
//        tabNames.add(tabName2);
//        return tabNames;
//    }
//
//    private List<TabName> getListTabname(TabName tabName0, TabName tabName1, TabName tabName2, TabName tabName3) {
////        重载
//        List<TabName> tabNames = new ArrayList<>();
//        tabNames.add(tabName0);
//        tabNames.add(tabName1);
//        tabNames.add(tabName2);
//        tabNames.add(tabName3);
//        return tabNames;
//    }
//
//    private List<TabName> getListTabname(TabName tabName0, TabName tabName1, TabName tabName2, TabName tabName3, TabName tabName4) {
////        重载
//        List<TabName> tabNames = new ArrayList<>();
//        tabNames.add(tabName0);
//        tabNames.add(tabName1);
//        tabNames.add(tabName2);
//        tabNames.add(tabName3);
//        tabNames.add(tabName4);
//        return tabNames;
//    }
//
//    public Set<Integer> searchAllProduct(String key) {
//        //   寻找数据 寻找地址应该是服务器 此时是伪代码 代码应该位于服务器
//        Set<Integer> set = new HashSet<>();
//        if (this.products != null) {
//            for (Commodity product : products) {
//                if (product.getComLabel().contains(key)) {
//                    set.add((int) product.getId());
//                }
//            }
//        }
//        return set;
//    }
}
