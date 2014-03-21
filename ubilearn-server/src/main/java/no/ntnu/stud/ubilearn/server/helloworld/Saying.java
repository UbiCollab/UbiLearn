package no.ntnu.stud.ubilearn.server.helloworld;

public class Saying {
    private final long id;
    private final String content;
    private final String test;
    
    public Saying(long id, String content, String test) {
        this.id = id;
        this.content = content;
        this.test = test;
    }

    public long getId() {
        return id;
    }

    public String getContents() {
        return content;
    }
    public String getTest(){
		return test;
    	
    }
}