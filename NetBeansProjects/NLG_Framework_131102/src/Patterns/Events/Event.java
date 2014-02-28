/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Patterns.Events;

import NLG_pkg.Message;
import java.util.ArrayList;
import org.joda.time.DateTime;
import org.joda.time.Duration;

/**
 *
 * @author hadi
 */
public abstract class Event {

    public String name;
    public DateTime starttime;
    public DateTime endtime;
    public double maxval;
    public double minval;
    private Duration duration;
    public ArrayList<Message> msg;

    public Event() {
        msg = new ArrayList<>();

    }

    public void setDuration() {
        this.duration = new Duration(this.starttime, this.endtime);
    }

    public Duration getDuration() {
        this.duration = new Duration(this.starttime, this.endtime);
        return this.duration;
    }

    /**
     *
     */
    //public abstract void SetMsgParams();

   
}
