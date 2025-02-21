/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;


import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;

public class Climb extends TimedRobot {
      
      
    
        // 非靜態內部類
        
    
    




    public static void wait(int ms) {//方便你們使用wait(毫秒);就能等待一定的時間
        try {
          Thread.sleep(ms);
        } catch (InterruptedException ex) {
          Thread.currentThread().interrupt();
        }
      }
  public final Joystick joystick = new Joystick(0);
  private SparkMax motor;
  private SparkMaxConfig motorConfig;
  private SparkClosedLoopController closedLoopController;
  private RelativeEncoder encoder;
  public Climb() {
        /*
     * 初始化 SPARK MAX，並獲取其編碼器和閉環控制器對象，以便稍後使用。
     */
    motor = new SparkMax(1, MotorType.kBrushless);
    closedLoopController = motor.getClosedLoopController();
    encoder = motor.getEncoder();
    /*
     * 創建一個新的 SPARK MAX 配置對象。這將存儲我們將在下面設置的 SPARK MAX 配置參數。
     */
    motorConfig = new SparkMaxConfig();
    /*
     * 配置編碼器。對於這個具體的例子，我們使用 NEO 的內建編碼器，並且不需要對其進行配置。
     * 如果需要，我們可以調整位置或速度的轉換因子等參數。
     */
    motorConfig.encoder
        .positionConversionFactor(1)
        .velocityConversionFactor(1);
    /*
     * 配置閉環控制器。我們需要確保將回饋感測器設置為主要編碼器。
     */
    motorConfig.closedLoop
        .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
        // 設置位置控制的 PID 值。我們不需要傳遞閉環槽，因為它將默認為槽 0。
        .p(0.1)
        .i(0)
        .d(0)
        .outputRange(-1, 1)
        // 設置速度控制的 PID 值，在槽 1 中
        .p(0.0001, ClosedLoopSlot.kSlot1)
        .i(0, ClosedLoopSlot.kSlot1)
        .d(0, ClosedLoopSlot.kSlot1)
        .velocityFF(1.0 / 5767, ClosedLoopSlot.kSlot1)
        .outputRange(-1, 1, ClosedLoopSlot.kSlot1);

    /*
     * 將配置應用到 SPARK MAX。
     *
     * kResetSafeParameters 用於將 SPARK MAX 重置為已知狀態。這在替換 SPARK MAX 時非常有用。
     *
     * kPersistParameters 用於確保在 SPARK MAX 斷電後配置不會丟失。這對於可能在運行中發生的電源循環非常有用。
     */
    motor.configure(motorConfig, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
  }
  public class TimedThread extends Thread {
    private long duration; // 執行時間，單位為毫秒
    private long startTime;

    public TimedThread(long duration) {
        long using_time = 100;
        duration = using_time;
    }

    @Override
    public void run() {
        if(joystick.getRawButtonPressed(1)){
            //按鈕被按下
            //DO SOMETHING
        
        startTime = System.currentTimeMillis(); // 記錄開始時間

        while (System.currentTimeMillis() - startTime < duration) {
            // 執行任務
            double targetVelocity = 1;
            closedLoopController.setReference(targetVelocity, ControlType.kVelocity, ClosedLoopSlot.kSlot1);
            System.out.println("線程仍在運行...");
            try {
                Thread.sleep(500); // 這裡每 500 毫秒執行一次任務
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("時間過了，停止執行");
    }
}
}

public static void main(String[] args) {
    try (Climb climb = new Climb()) {
        Climb.TimedThread thread = climb.new TimedThread(5000); // 使用 Climb 實例來創建 TimedThread
        thread.start(); // 啟動線程
    }
}
}
