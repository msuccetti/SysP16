package robot.drivers;

public class WlanMessage
{
	/**
	 * types of messages 
	 *
	 */
	public enum Type
	{
		COMMAND,
		CODE,
		EMPTY,
		ILLEGAL;
	}

	/**
	 * the type of the message 
	 */
	public Type type=Type.ILLEGAL;
	/**
	 * the content of the message
	 */
	public int content=0; 
	/**
	 * the header of the message
	 */
	public int header=0; 
	/**
	 * true -> the message is valid, false otherwise
	 */
	public boolean valid=false;
}
