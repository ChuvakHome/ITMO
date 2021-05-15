
public class Lab1 
{
	public static void main(String[] args)
	{
		final int D_LENGTH = (20 - 4) / 2 + 1;
		final int X_LENGTH = 13;
		
		int i, j;
		
		long[] d = new long[D_LENGTH];
		
		for (i = 0; i < D_LENGTH; ++i)
			d[i] = (i + 2) * 2;
		
		double[] x = new double[X_LENGTH];
		
		for (i = 0; i < X_LENGTH; ++i)
			x[i] = Math.random() * (11.0 - (-4.0)) - 4.0;
		
		double[][] d1 = new double[D_LENGTH][X_LENGTH];
		
		for (i = 0; i < D_LENGTH; ++i)
		{
			for (j = 0; j < X_LENGTH; ++j)
			{
				if (d[i] == 20)
					d1[i][j] = Math.asin(1 / Math.exp(Math.abs(Math.exp(Math.tan(x[j])))));
				else if (d[i] == 4 || d[i] == 8 || d[i] == 10 || d[i] == 14)
					d1[i][j] = Math.log(Math.sqrt(Math.abs(x[j])) / Math.PI / 2);
				else
					d1[i][j] = Math.log(Math.sqrt(Math.pow(Math.abs(x[j]), x[j])) / 2);
			}
		}
		
		for (i = 0; i < D_LENGTH; ++i)
		{
			for (j = 0; j < X_LENGTH; ++j)
				System.out.printf("%.5f     ", d1[i][j]);
			
			System.out.println();
		}
	}
}
