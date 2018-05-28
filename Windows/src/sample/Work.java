package sample;

import java.util.ArrayList;
import java.util.Random;

public class Work {
    //页面类
    public class Page{
        //页面类
        Integer pageID;
        ArrayList<Instruct> instructs;
        Integer memoryNum;
        boolean inMemory;

        public Integer getPageID() {
            return pageID;
        }

        public void setPageID(Integer pageID) {
            this.pageID = pageID;
        }
        public Page(Integer pageID, ArrayList<Instruct> instructs) {
            this.pageID = pageID;
            this.instructs = instructs;
        }

        public ArrayList<Instruct> getInstructs() {
            return instructs;
        }

        public void setInstructs(ArrayList<Instruct> instructs) {
            this.instructs = instructs;
        }

        public Integer getMemoryNum() {
            return memoryNum;
        }

        public void setMemoryNum(Integer memoryNum) {
            this.memoryNum = memoryNum;
        }

        public boolean isInMemory() {
            return inMemory;
        }

        public void setInMemory(boolean inMemory) {
            this.inMemory = inMemory;
        }

        public Page(Integer pageID) {
            this.pageID = pageID;
            this.instructs = new ArrayList<>();
            this.memoryNum = 0;
            this.inMemory = false;
        }
    }
    public ArrayList<Page> getPages() {
        return pages;
    }
    public void setPages(ArrayList<Page> pages) {
        this.pages = pages;
    }
    //指令类
    public class Instruct{
        Integer instructNum;
        boolean isExecute;
        public Instruct(Integer instructNum, boolean isExecute) {
            this.instructNum = instructNum;
            this.isExecute = isExecute;
        }
    }
    //共320条指令
    public final Integer instructNum = 320;
    public final Integer minInstruct = 0;
    public final Integer maxInstruct = 319;
    //每页存储10条指令
    public final Integer instructsInEachPage = 10;
    //未执行的指令数
    public Integer remainedInstruct = 320;
    //执行次数
    public Integer executeCount = 0;
    //首条指令
    public Integer firstInstruct = new Random().nextInt(maxInstruct);
    //存储页面
    private ArrayList<Page> pages;
    //构造函数
    public Work(ArrayList<Page> pages){
        this.pages = pages;
    }
    //默认构造函数，初始化页面
    public Work() {
        pages = new ArrayList<>();
        for (int i = 0; i < instructNum/instructsInEachPage; i++){
            Page page = new Page(i);
            for (int j = 0; j < instructsInEachPage; j++){
                page.instructs.add(new Instruct(i*instructsInEachPage+j, false));
            }
            pages.add(page);
        }
    }
    //完成一个指令，当该指令未被执行过，remainedInstruct减一
    public void finishAnInstruct(){
        remainedInstruct--;
    }
    //判断作业是否完成
    public boolean finishWork(){
        return remainedInstruct == 0;
    }
}
