package ths;

public class PlayerCellChangeEvent extends Event {

	private String cellName;
	private String cellID;
	
	public PlayerCellChangeEvent(String line) {
		super(line);
		// TODO Auto-generated constructor stub
	}
	
	public String getCellName() {
		return cellName;
	}

	public void setCellName(String cellName) {
		this.cellName = cellName;
	}

	public String getCellID() {
		return cellID;
	}

	public void setCellID(String cellID) {
		this.cellID = cellID;
	}
	
	@Override
	protected void parse() {
		// TODO Auto-generated method stub
		super.parse();		
	}

	@Override
	public String toString() {

		return "";
	}

}
