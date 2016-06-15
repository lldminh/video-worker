package com.golftec.teaching.model.lesson;

import com.golftec.teaching.model.types.EnvironmentType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Lessons belong to environments so to get lessons, the environment model needs to be created first
 * and then add lessons to the environment model.
 *
 * @author Al Wells
 */
public class Lesson implements Serializable {

    private String workflowId;
    private String studentId;
    private EnvironmentType environmentType;
    /**
     * While other params are self-explanatory, this one
     * deals with the text that is shown in the telestrator,
     * particularly the coaches notes and recommendations.
     */
    private String script;
    /**
     * @see Swing
     */
    private List<Swing> swings = new ArrayList<Swing>();
    private Student student;

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public EnvironmentType getEnvironmentType() {
        return environmentType;
    }

    public void setEnvironmentType(EnvironmentType environmentType) {
        this.environmentType = environmentType;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public List<Swing> getSwings() {
        return swings;
    }

    public void setSwing(Swing swing) {
        swings.add(swing);
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
