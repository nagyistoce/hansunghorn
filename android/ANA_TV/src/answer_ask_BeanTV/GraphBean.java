package answer_ask_BeanTV;

public class GraphBean {
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getAnswer() {
		return Answer;
	}
	public void setAnswer(String answer) {
		Answer = answer;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	private  String response="";
	private String Answer="";
	private Integer count=0;
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	private int page=0;
	//private int index=0;
}
