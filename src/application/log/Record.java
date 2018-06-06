package application.log;

public class Record {
	private String idProcess;
	private String record;
	private String resource;
	
	public Record(){
		this.idProcess = "";
		this.record = "";
		this.resource = "";
	}
	
	public Record(String idProcess, String record, String resource){
		this.idProcess = idProcess;
		this.record = record;
		this.resource = resource;
	}
	
	public void setIdProcess(String idProcess) {
		this.idProcess = idProcess;
	}
	
	public void setRecord(String record) {
		this.record = record;
	}
	
	public String getIdProcess() {
		return this.idProcess;
	}
	
	public String getRecord() {
		return this.record;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}
}