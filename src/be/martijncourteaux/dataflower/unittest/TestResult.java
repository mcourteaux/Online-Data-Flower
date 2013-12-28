package be.martijncourteaux.dataflower.unittest;

public class TestResult
{
	
	public int expected;
	public int result;

	public TestResult(int expected, int result)
	{
		this.expected = expected;
		this.result = result;
	}
	
	public int getAbsoluteError()
	{
		return Math.abs(expected - result);
	}
}
