package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.Random;

public class Controller {
    @FXML
    private TextArea instructDetail;
    @FXML
    private TextArea workDetail;
    @FXML
    private Label page1WithoutExecute;
    @FXML
    private Label page2WithoutExecute;
    @FXML
    private Label page3WithoutExecute;
    @FXML
    private Label page4WithoutExecute;
    @FXML
    private Label page1WithExecute;
    @FXML
    private Label page2WithExecute;
    @FXML
    private Label page3WithExecute;
    @FXML
    private Label page4WithExecute;

    private Work work;
    private Memory memory;
    private int step = 0;

    @FXML
    void executeOne() throws InterruptedException {
        instructDetail.setText("");
        if (work == null && memory == null){
            instructDetail.setEditable(false);
            work = new Work();
            memory = new Memory();
        }
        ArrayList<Integer> ps = new ArrayList<>();
        for (int i = 0; i < memory.MemoryBlockNum; i++){
            ps.add(memory.getMemoryBlocks().get(i).pageID);
        }
        assert work != null;
        if (!work.finishWork()){
            int m = work.firstInstruct;
            String detail = "";
            if (step == 0){
                //0到m-1指令中随机执行
                work.firstInstruct = new Random().nextInt(m);
                m = work.firstInstruct;
                detail = memory.executeAnInstruct(work, m);
                step++;
            }
            else if (!work.finishWork() && step == 1){
                detail = memory.executeAnInstruct(work, m+1);
                step++;
            }
            else if (!work.finishWork() && step == 2){
                //m-1到319指令中随机执行
                work.firstInstruct = (new Random().nextInt(work.maxInstruct - m)) + m;
                m = work.firstInstruct;
                detail = memory.executeAnInstruct(work, m);
                step++;
            }
            else if (!work.finishWork() && step == 3){
                detail = memory.executeAnInstruct(work, m+1);
                step=0;
            }
            instructDetail.setText(detail);
            for (int i = 0; i < memory.MemoryBlockNum; i++){
                ps.add(memory.getMemoryBlocks().get(i).pageID);
            }
            updatePage(ps.get(0), ps.get(1), ps.get(2), ps.get(3), ps.get(4), ps.get(5), ps.get(6), ps.get(7));
        }
    }
    private void updatePage(int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8){
        page1WithoutExecute.setText(p1 == -1 ? "空" : String.valueOf(p1));
        page2WithoutExecute.setText(p2 == -1 ? "空" : String.valueOf(p2));
        page3WithoutExecute.setText(p3 == -1 ? "空" : String.valueOf(p3));
        page4WithoutExecute.setText(p4 == -1 ? "空" : String.valueOf(p4));
        page1WithExecute.setText(p5 == -1 ? "空" : String.valueOf(p5));
        page2WithExecute.setText(p6 == -1 ? "空" : String.valueOf(p6));
        page3WithExecute.setText(p7 == -1 ? "空" : String.valueOf(p7));
        page4WithExecute.setText(p8 == -1 ? "空" : String.valueOf(p8));
    }
    @FXML
    void executeAll() throws InterruptedException {
        Work work = new Work();
        Memory memory = new Memory();
        while (!work.finishWork()){
            int m = work.firstInstruct;
            //0到m-1指令中随机执行
            work.firstInstruct = new Random().nextInt(m);
            m = work.firstInstruct;
            appendToWorkDetail(memory.executeAnInstruct(work, m), work);
            if (!work.finishWork()){
                appendToWorkDetail(memory.executeAnInstruct(work, m+1), work);
            }
            if (!work.finishWork()){
                //m-1到319指令中随机执行
                work.firstInstruct = (new Random().nextInt(work.maxInstruct - m)) + m;
                m = work.firstInstruct;
                appendToWorkDetail(memory.executeAnInstruct(work, m),work);
            }
            if (!work.finishWork()){
                appendToWorkDetail(memory.executeAnInstruct(work, m+1), work);
            }
        }
        workDetail.appendText("本次作业共执行了" + work.executeCount + "次。");
        work.executeCount = 0;
    }
    private void appendToWorkDetail(String detail, Work work){
        workDetail.appendText("Step" + String.valueOf(work.executeCount) + "：");
        workDetail.appendText(detail.replace("\n", ","));
        workDetail.appendText("\n");
    }
}
