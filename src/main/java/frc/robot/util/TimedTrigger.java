package frc.robot.util;

public class TimedTrigger {

  private double mTriggerDelay = 0.0; // (seconds)
  private double mTimeTriggered = Double.NaN; // Time since trigger was activated (seconds)

  /**
   * @param triggerDelay Delay in seconds.
   */
  public TimedTrigger(double triggerDelay) {
    mTriggerDelay = triggerDelay;
  }

  public void trigger() {
    mTimeTriggered = getCurrentTime();
  }

  public void reset() {
    mTimeTriggered = Double.NaN;
  }

  /**
   * Get the state of the trigger.
   * 
   * @param autoReset Reset trigger if true.
   * @return State of the trigger.
   */
  public boolean get(boolean autoReset) {
    if (!Double.isFinite(mTimeTriggered)) return false;

    boolean state = getCurrentTime() - mTimeTriggered >= mTriggerDelay;

    if (autoReset && state) reset();

    return state;
  }

  /**
   * @return Current time in seconds.
   */
  private static double getCurrentTime() { return System.currentTimeMillis() / 1000.0; }

}