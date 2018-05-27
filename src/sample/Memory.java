package sample;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class Memory {
    public final Integer minMemoryBlock = 1;
    public final Integer maxMemoryBlock = 4;
    public final Integer MemoryBlockNum = 4;
    private final int sleepTime = 0;

//    private TextArea textArea;

    public class Block{
        Integer ID;
        boolean isNull;
        Integer pageID;

        public Integer getID() {
            return ID;
        }

        public void setID(Integer ID) {
            this.ID = ID;
        }

        public boolean isNull() {
            return isNull;
        }

        public void setNull(boolean aNull) {
            isNull = aNull;
        }

        public Integer getPageID() {
            return pageID;
        }

        public void setPageID(Integer pageID) {
            this.pageID = pageID;
        }

        Block(Integer ID){
            this.ID = ID;
            this.isNull = true;
            this.pageID = -1;
        }
    }
    private LinkedBlockingQueue<Work.Page> pageQueue;
    private ArrayList<Block> memoryBlocks;

    public Queue<Work.Page> getPageQueue() {
        return pageQueue;
    }

    public void setPageQueue(LinkedBlockingQueue<Work.Page> pageQueue) {
        this.pageQueue = pageQueue;
    }

    public ArrayList<Block> getMemoryBlocks() {
        return memoryBlocks;
    }

    public void setMemoryBlocks(ArrayList<Block> memoryBlocks) {
        this.memoryBlocks = memoryBlocks;
    }

    public Memory(LinkedBlockingQueue<Work.Page> pageQueue, ArrayList<Block> memoryBlocks) {
        this.pageQueue = pageQueue;
        this.memoryBlocks = memoryBlocks;
    }

    public Memory(){
        this.pageQueue = new LinkedBlockingQueue<>();
        this.memoryBlocks = new ArrayList<>();
        for (int i = minMemoryBlock; i <= maxMemoryBlock; i++){
            memoryBlocks.add(new Block(i));
        }
//        this.textArea = textArea;
    }

    public String executeAnInstruct(Work work, Integer instruct) throws InterruptedException {
//        System.out.println("当前执行指令："+instruct);
        Thread.sleep(sleepTime);
//        Platform.runLater(()->textArea.appendText("当前剩余指令数："+work.remainedInstruct+"\n"));
        Work.executeCount++;
        System.out.println("当前剩余指令数："+work.remainedInstruct);
        int pageNum = instruct/work.instructsInEachPage;
        int instructNum = instruct - pageNum*work.instructsInEachPage;
        StringBuilder stringBuilder = new StringBuilder().append("执行指令").append(instruct).append("，指令位于页面").append(pageNum).append("，页面偏移为").append(instructNum).append("\n");
        Work.Page page = work.getPages().get(pageNum);
        //若该指令未被执行过，则将其标记为已被执行，且剩余未执行指令减少1
        if (!page.getInstructs().get(instructNum).isExecute){
            page.getInstructs().get(instructNum).isExecute = true;
            work.finishAnInstruct();
            stringBuilder.append("该指令未被执行过，");
        }else {
            stringBuilder.append("该指令已被执行过，");
        }
        if (!page.inMemory){
            if (pageQueue.size() == MemoryBlockNum){
                Work.Page lostPage = pageQueue.poll();
                assert lostPage != null;
                stringBuilder.append("四个内存块已被占满\n将页面").append(lostPage.pageID).append("置换出来，\n将页面").append(pageNum).append("放入").append(lostPage.memoryNum).append("号内存块中，并执行该指令。");
                page.memoryNum = lostPage.memoryNum;
                page.inMemory = true;
                memoryBlocks.get(lostPage.memoryNum - 1).isNull = false;
                memoryBlocks.get(lostPage.memoryNum - 1).pageID = page.pageID;
                try {
                    pageQueue.put(page);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                System.out.println("页面" + lostPage.pageID + "被被置换，" + "页面" + page.pageID + "被加入内存块" + memoryBlocks.get(lostPage.memoryNum - 1).ID);
//                Thread.sleep(500);
            }else {
                int memoryNum = pageQueue.size() + 1;
                stringBuilder.append("内存块仍未占满\n将页面").append(pageNum).append("放入").append(memoryNum).append("号内存块中，并执行该指令。");
                page.memoryNum = memoryNum;
                page.inMemory = true;
                memoryBlocks.get(memoryNum - 1).isNull = false;
                memoryBlocks.get(memoryNum - 1).pageID = page.pageID;
                pageQueue.put(page);
//                System.out.println("页面" + page.pageID + "被加入内存块" + memoryBlocks.get(page.memoryNum - 1).ID);
//                Thread.sleep(500);
            }
        }else {
            stringBuilder.append("页面").append(pageNum).append("已在内存块中，直接执行指令。");
        }
        return stringBuilder.toString();
    }
}
