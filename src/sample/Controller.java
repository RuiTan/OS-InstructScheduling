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
        updatePageWithoutExecute();
        if (work == null && memory == null){
            instructDetail.setEditable(false);
            work = new Work();
            memory = new Memory();
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
            updatePageWithExecute();
        }
    }

    void updatePageWithoutExecute(){
        page1WithoutExecute.setText(memory.getMemoryBlocks().get(0).isNull ? "空" : String.valueOf(memory.getMemoryBlocks().get(0).pageID));
        page2WithoutExecute.setText(memory.getMemoryBlocks().get(0).isNull ? "空" : String.valueOf(memory.getMemoryBlocks().get(1).pageID));
        page3WithoutExecute.setText(memory.getMemoryBlocks().get(0).isNull ? "空" : String.valueOf(memory.getMemoryBlocks().get(2).pageID));
        page4WithoutExecute.setText(memory.getMemoryBlocks().get(0).isNull ? "空" : String.valueOf(memory.getMemoryBlocks().get(3).pageID));
    }

    void updatePageWithExecute(){
        page1WithExecute.setText(memory.getMemoryBlocks().get(0).isNull ? "空" : String.valueOf(memory.getMemoryBlocks().get(0).pageID));
        page2WithExecute.setText(memory.getMemoryBlocks().get(0).isNull ? "空" : String.valueOf(memory.getMemoryBlocks().get(1).pageID));
        page3WithExecute.setText(memory.getMemoryBlocks().get(0).isNull ? "空" : String.valueOf(memory.getMemoryBlocks().get(2).pageID));
        page4WithExecute.setText(memory.getMemoryBlocks().get(0).isNull ? "空" : String.valueOf(memory.getMemoryBlocks().get(3).pageID));
    }
}
