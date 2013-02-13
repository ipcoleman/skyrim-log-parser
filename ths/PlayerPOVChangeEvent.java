package ths;

public class PlayerPOVChangeEvent extends Event {

	private String pov;
	
	public PlayerPOVChangeEvent() {
		// TODO Auto-generated constructor stub
		super("");
	}
	
	public PlayerPOVChangeEvent(String line)
	{
		super(line);
		parse();
	}
	
	public String getPov() {
		return pov;
	}

	public void setPov(String pov) {
		this.pov = pov;
	}

	@Override
	protected void parse() {
		// TODO Auto-generated method stub
//		super.parse();
		try {
			parsePOVChange();
		} catch (IncorrectTagException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void parsePOVChange() throws IncorrectTagException
	{
		if(this.tag.equals("PLAYER_POV_CHANGE"))
		{
			setPov(parsePOV());
		}
		else
		{
			throw new IncorrectTagException();
		}
	}
	
	private String parsePOV()
	{
		String newPov = "";
		newPov = line.split(" ")[0];
		
		return newPov;	
	}
	
	@Override
	public String toString() {
		String str = "";
		str = str.concat(super.toString());
		str = str.concat("POV: " + this.pov + "\n");
		str = str.concat("------------------\n");
		
		return str;
	}
}
