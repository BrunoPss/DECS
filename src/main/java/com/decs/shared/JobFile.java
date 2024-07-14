package com.decs.shared;

import java.io.Serializable;

/**
 * <b>Job File Class</b>
 * <p>
 *     This class represents a file related to a specific Job.
 * </p>
 * @author Bruno Guiomar
 * @version 1.0
 */
public class JobFile implements Serializable {
    //Internal Data
    private static final long serialVersionUID = 1L;
    private String name;
    private String type;
    private byte[] content;

    /**
     * Class Constructor
     * @param name File name
     * @param type File type
     * @param content File binary content
     */
    public JobFile(String name, String type, byte[] content) {
        this.name = name;
        this.type = type;
        this.content = content;
    }

    //Get Methods
    public String getName() { return this.name; }
    public String getType() { return this.type; }
    public byte[] getFileBytes() { return this.content; }

    //Set Methods


    //Methods


    //Overrides


    //Internal Functions

}