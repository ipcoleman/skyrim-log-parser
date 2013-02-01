package ths;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String line = null;
//		String tag = null;
		Parser parser = new Parser("0.0.log");
//		String tags[] = new String[100];
//		while((line = parser.nextLine()) != null)
//		{
			parser.parse();	
			parser.printIntervalOfPlayerMoveEvents();
			
			
			
//			tag = parser.getTag(line);
//			System.out.println(tag);
//			if(!parser.containsTag(tag))
//			{
//				parser.addTag(tag);
//				System.out.println(line);
//				if(tag.equals("PLAYER_MOVE"))
//				{
//					try {
//						parser.getPlayerMove(line);
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			}
//		}
	}
}
