/**
 * ģ��ALU���������͸���������������
 * @151250160_�⾲��[�뽫�˴��޸�Ϊ��ѧ��_������]
 *
 */

public class ALU {

	/**
	 * ����ʮ���������Ķ����Ʋ����ʾ��<br/>
	 * ����integerRepresentation("9", 8)
	 * @param number ʮ������������Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 * @param length �����Ʋ����ʾ�ĳ���
	 * @return number�Ķ����Ʋ����ʾ������Ϊlength
	 */
	public String integerRepresentation (String number, int length) {
		// TODO YOUR CODE HERE.
		String result = "";
		int num = Integer.parseInt(number);
		if(num<0){
			int i = (int) (Math.pow(2,length-1)+num);
			result = "1"+integerRepresentation(Integer.toString(i),length-1);
		}else{
			for(int i = 0;i<length;i++){
				if(num%2==1){
					result = "1"+result;
				}else{
					result = "0"+result;
				}
				
				num = num/2;
			}
		}
		return result;
	}
	
	/**
	 * ����ʮ���Ƹ������Ķ����Ʊ�ʾ��
	 * ��Ҫ���� 0������񻯡����������+Inf���͡�-Inf������ NaN�����أ������� IEEE 754��
	 * �������Ϊ��0���롣<br/>
	 * ����floatRepresentation("11.375", 8, 11)
	 * @param number ʮ���Ƹ�����������С���㡣��Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return number�Ķ����Ʊ�ʾ������Ϊ 1+eLength+sLength���������ң�����Ϊ���š�ָ���������ʾ����β������λ���أ�
	 */
	public String floatRepresentation (String number, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		Double num = Double.parseDouble(number);
		int half = (int)(Math.pow(2, eLength-1)-1);
		
		int integ = (int)Math.abs(num);
		Double doub = Math.abs(num)-integ;//ȫ��Ϊ��
		//�Ƚ��Ƿ񳬳����Ա�ʾ�����ֵ
		if(Math.abs(num)>((2-Math.pow(2, -sLength))*Math.pow(2, half))){
			String eTemp = "";
			String sTemp = "";
			
			for(int i=0;i<eLength;i++){
				eTemp += "1";
			}
			
			for(int j=0;j<sLength;j++){
				sTemp += "0";
			}
			if(num>0)
				return "0"+eTemp+sTemp;
			else
				return "1"+eTemp+sTemp;
		}//��ı�ʾ����
		else if(Math.abs(num)<Math.pow(2, 1-half)){
			String temp;
			if(number.trim().charAt(0) == '-')
				temp = "1";
			else
				temp = "0";
			for(int i=0;i<sLength+eLength;i++)
				temp += "0";
			return temp;
		}
		
		String symbol;//symbol�Ƿ���λ��0����Ǹ�����1������
		if(num>=0)
			symbol = "0";
		else
			symbol = "1";
		
		String integStr = "";//����������ֵĶ����Ʊ�ʾ
		while(integ!=0){
			if(integ%2==1){
				integStr = "1"+integStr;
			}else{
				integStr = "0"+integStr;
			}
			
			integ = integ/2;
		}
		integ = (int)Math.abs(num);//���³�ʼ��
		
		String doubStr = "";
		for(int i = 0;i<sLength+half-1;i++){
			doub = doub*2;
			if(doub<1){
				doubStr = doubStr+"0";
			}else{
				doubStr = doubStr+"1";
				doub = doub-1;
			}
		}
		doub = Math.abs(num)-integ;//���³�ʼ��
		
		//���������ı�ʾ
		if(Math.abs(num)<Math.pow(2, -half+1)){
			String eTemp = "";
			
			for(int i=0;i<eLength;i++){
				eTemp += "0";
			}
			String sTemp = doubStr.substring(half-1);
			
			return symbol+eTemp+sTemp;
		}
		//�������ֶ����Ʊ�ʾ�ĳ��ȼ�1����half����ָ�����ֱ�ʾ����
		if(integ>0){
			String eTemp = integerRepresentation(Integer.toString(integStr.length()-1+half), eLength);
			
			String sTemp = integStr.substring(1)+doubStr;
			
			sTemp = sTemp.substring(0, sLength);
			
			return symbol+eTemp+sTemp;
		}
		//С�����ֵ�1������
		if(integ==0){
			int exponent = half - 1 - doubStr.indexOf("1");
			String eTemp = integerRepresentation(Integer.toString(exponent),eLength);
			String sTemp = doubStr.substring(doubStr.indexOf("1")+1);
			if(sTemp.length()<sLength){
				for(int i =0;i<sLength-doubStr.substring(doubStr.indexOf("1")+1).length();i++){
					sTemp+="0";
				}
			}else{
				sTemp = sTemp.substring(0,sLength);
			}

			return symbol+eTemp+sTemp;
		}
		
		return null;
	}
	
	/**
	 * ����ʮ���Ƹ�������IEEE 754��ʾ��Ҫ�����{@link #floatRepresentation(String, int, int) floatRepresentation}ʵ�֡�<br/>
	 * ����ieee754("11.375", 32)
	 * @param number ʮ���Ƹ�����������С���㡣��Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 * @param length �����Ʊ�ʾ�ĳ��ȣ�Ϊ32��64
	 * @return number��IEEE 754��ʾ������Ϊlength���������ң�����Ϊ���š�ָ���������ʾ����β������λ���أ�
	 */
	public String ieee754 (String number, int length) {
		// TODO YOUR CODE HERE.
		if(length==32){
			return floatRepresentation(number,8,23);
		}else
		if(length==64){
			return floatRepresentation(number,11,52);
		}
		return "Wrong!";
	}
	
	/**
	 * ��������Ʋ����ʾ����������ֵ��<br/>
	 * ����integerTrueValue("00001001")
	 * @param operand �����Ʋ����ʾ�Ĳ�����
	 * @return operand����ֵ����Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 */
	public String integerTrueValue (String operand) {
		// TODO YOUR CODE HERE.
		char[] numChar = operand.toCharArray();
		int num = 0;
		for(int i = 0;i<operand.length()-1;i++){
			if(numChar[operand.length()-1-i]=='1'){
				num = num+(int)(Math.pow(2, i));
			}
		}
		if(numChar[0]=='1'){
			num = num-(int) (Math.pow(2, operand.length()-1));
		}
		return Integer.toString(num);
	}
	
	/**
	 * ���������ԭ���ʾ�ĸ���������ֵ��<br/>
	 * ����floatTrueValue("01000001001101100000", 8, 11)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return operand����ֵ����Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ����������ֱ��ʾΪ��+Inf���͡�-Inf���� NaN��ʾΪ��NaN��
	 */
	public String floatTrueValue (String operand, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		char c = operand.charAt(0);
		String symbol = "";
		if(c=='1')
			symbol = "-";
		
		//�޷���λ
		String exponentStr = operand.substring(1,eLength+1);
		int exponent = 0;
		for(int i=0;i<exponentStr.length();i++)
			exponent = exponent * 2 + (exponentStr.charAt(i)-'0'); 
		
		// ���β��λ�����ʾ��ֵ
		String significandStr = operand.substring(eLength+1,eLength+sLength+1);
		int significandResult = 0;
		for(int i=0;i<significandStr.length();i++)
			significandResult = significandResult * 2 + (significandStr.charAt(i)-48);
		double f = significandResult / Math.pow(2, sLength);
		
		if(exponent==0){
			// ����0�����
			if(f == 0.0){
				return symbol + "0";
			}
			// ����������������
			else{
				int exponentReal = (int)(exponent - (Math.pow(2, eLength-1)-1));
				return symbol + (f * Math.pow(2, exponentReal));
			}
		}
		
		else if(exponent==Math.pow(2, eLength)-1){
			// 6.3.2��1���������������
			if(f == 0.0){
				if(symbol.equals("-"))
					return "-Inf";
				else
					return "+Inf";//����������
			}
			//6.3.2��2��������֪ͨʽ���������
			else{
				return "NaN";
			}
		}
		
		else{
			int exponentReal = (int)(exponent - (Math.pow(2, eLength-1)-1));
			return symbol + ((f+1) * Math.pow(2, exponentReal));
		}
	}
	
	/**
	 * ��λȡ��������<br/>
	 * ����negation("00001001")
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @return operand��λȡ���Ľ��
	 */
	public String negation (String operand) {
		// TODO YOUR CODE HERE.
		String result = "";
		for(int i = 0;i<operand.length();i++){
			int a = 1-operand.charAt(i)+'0';
			result = result+Integer.toString(a);
		}
		return result;
	}
	
	/**
	 * ���Ʋ�����<br/>
	 * ����leftShift("00001001", 2)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param n ���Ƶ�λ��
	 * @return operand����nλ�Ľ��
	 */
	public String leftShift (String operand, int n) {
		// TODO YOUR CODE HERE.
		String result = operand;
		int length = operand.length()-1;
		while(n!=0){
			result = result.substring(1)+"0";
			n--;
		}
		return result;
	}
	
	/**
	 * �߼����Ʋ�����<br/>
	 * ����logRightShift("11110110", 2)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param n ���Ƶ�λ��
	 * @return operand�߼�����nλ�Ľ��
	 */
	public String logRightShift (String operand, int n) {
		// TODO YOUR CODE HERE.
		String result = operand;
		int length = operand.length()-1;
		while(n!=0){
			result = "0"+result.substring(0,length);
			n--;
		}
		return result;
	}
	
	/**
	 * �������Ʋ�����<br/>
	 * ����logRightShift("11110110", 2)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param n ���Ƶ�λ��
	 * @return operand��������nλ�Ľ��
	 */
	public String ariRightShift (String operand, int n) {
		// TODO YOUR CODE HERE.
		char symbol = operand.charAt(0);
		String result = operand;
		int length = operand.length()-1;
		while(n!=0){
			result = symbol+result.substring(0,length);
			n--;
		}
		return result;
	}
	
	/**
	 * ȫ����������λ�Լ���λ���мӷ����㡣<br/>
	 * ����fullAdder('1', '1', '0')
	 * @param x ��������ĳһλ��ȡ0��1
	 * @param y ������ĳһλ��ȡ0��1
	 * @param c ��λ�Ե�ǰλ�Ľ�λ��ȡ0��1
	 * @return ��ӵĽ�����ó���Ϊ2���ַ�����ʾ����1λ��ʾ��λ����2λ��ʾ��
	 */
	public String fullAdder (char x, char y, char c) {
		// TODO YOUR CODE HERE.
		String result = "";
		//sum
		result = Integer.toString((x-'0')^(y-'0')^(c-'0'));
		//carry
		result = Integer.toString(((x-'0')&(y-'0'))|((c-'0')&(y-'0'))|((x-'0')&(c-'0')))+result;
		return result;
	}
	
	/**
	 * 4λ���н�λ�ӷ�����Ҫ�����{@link #fullAdder(char, char, char) fullAdder}��ʵ��<br/>
	 * ����claAdder("1001", "0001", '1')
	 * @param operand1 4λ�����Ʊ�ʾ�ı�����
	 * @param operand2 4λ�����Ʊ�ʾ�ļ���
	 * @param c ��λ�Ե�ǰλ�Ľ�λ��ȡ0��1
	 * @return ����Ϊ5���ַ�����ʾ�ļ����������е�1λ�����λ��λ����4λ����ӽ�������н�λ��������ѭ�����
	 */
	public String claAdder (String operand1, String operand2, char c) {
		// TODO YOUR CODE HERE.
		String result = "";
		int[] P = new int[4];
		int[] G = new int[4];
		int[] C = new int[5];
		for(int i =0;i<operand1.length();i++){
			//���Pi
			P[i] = fullAdder(operand1.charAt(operand1.length()-1-i),operand2.charAt(operand1.length()-1-i),'1').charAt(0)-'0';
			//System.out.println(P[i]);
			//���Gi
			G[i] = fullAdder(operand1.charAt(operand1.length()-1-i),operand2.charAt(operand1.length()-1-i),'0').charAt(0)-'0';
		}
		
		C[0] = (int)(c - '0');
		C[1] = G[0] 
				|(P[0] & C[0]);
		C[2] = G[1] 
				|(P[1] & G[0]) 
				|(P[1] & P[0] & C[0]);
		C[3] = G[2] 
				|(P[2] & G[1]) 
				|(P[2] & P[1] & G[0]) 
				|(P[2] & P[1] & P[0] & C[0]);
		C[4] = G[3] 
				|(P[3] & G[2]) 
				|(P[3] & P[2] & G[1]) 
				|(P[3] & P[2] & P[1] & G[0]) 
				|(P[3] & P[2] & P[1] & P[0] & C[0]);
		
		for(int i =0;i<operand1.length();i++){
			result = Integer.toString((operand1.charAt(operand1.length()-1-i)-'0')^(operand2.charAt(operand1.length()-1-i)-'0')^C[i])+result;
		}
		result = Integer.toString(C[4])+result;
		return result;
	}
	
	/**
	 * ��һ����ʵ�ֲ�������1�����㡣
	 * ��Ҫ�������š����š�����ŵ�ģ�⣬
	 * ������ֱ�ӵ���{@link #fullAdder(char, char, char) fullAdder}��
	 * {@link #claAdder(String, String, char) claAdder}��
	 * {@link #adder(String, String, char, int) adder}��
	 * {@link #integerAddition(String, String, int) integerAddition}������<br/>
	 * ����oneAdder("00001001")
	 * @param operand �����Ʋ����ʾ�Ĳ�����
	 * @return operand��1�Ľ��������Ϊoperand�ĳ��ȼ�1�����е�1λָʾ�Ƿ���������Ϊ1������Ϊ0��������λΪ��ӽ��
	 */
	public String oneAdder (String operand) {
		// TODO YOUR CODE HERE.
		String result = "";
		int[] c = new int[operand.length()+1];
		c[0] = 1;
		for(int i = 0;i<operand.length();i++){
			result = (char)((operand.charAt(operand.length()-1-i)-'0')^c[i]+'0')+result;
			c[i+1] = (operand.charAt(operand.length()-1-i)-'0')&c[i];
		}
		char overflow = '0';
		overflow = (char) (overflow+(('1'-result.charAt(0))&(operand.charAt(0)-'0')|(result.charAt(0)-'0')&('1'-operand.charAt(0))));
		return overflow+result;
	}
	
	/**
	 * �ӷ�����Ҫ�����{@link #claAdder(String, String, char)}����ʵ�֡�<br/>
	 * ����adder("0100", "0011", ��0��, 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ļ���
	 * @param c ���λ��λ
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ����ӽ��
	 */
	public String adder (String operand1, String operand2, char c, int length) {
		// TODO YOUR CODE HERE.
		String formal1 = operand1;
		String formal2 = operand2;
		String result = "";
		
		for(int i = 0;i<length-operand1.length();i++){
			formal1 = operand1.charAt(0)+formal1;
		}
		
		for(int i = 0;i<length-operand2.length();i++){
			formal2 = operand2.charAt(0)+formal2;
		}
		
		for(int i = 0;i<length/4;i++){
			result = claAdder(formal1.substring(length-4*i-4,length-4*i),formal2.substring(length-4*i-4,length-4*i),c)+result;
			c = result.charAt(0);
			result = result.substring(1);
		}
		
		char overflow = '0';
		overflow = (char) (overflow+(('1'-result.charAt(0))&(formal1.charAt(0)-'0')&(formal2.charAt(0)-'0')|(result.charAt(0)-'0')&('1'-formal1.charAt(0))&('1'-formal2.charAt(0)-'0')));
		
		return overflow+result;
	}
	
	/**
	 * �����ӷ���Ҫ�����{@link #adder(String, String, char, int) adder}����ʵ�֡�<br/>
	 * ����integerAddition("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ļ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ����ӽ��
	 */
	public String integerAddition (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		return adder(operand1,operand2,'0',length);
		
	}
	
	/**
	 * �����������ɵ���{@link #integerAddition(String, String, char, int) integerAddition}����ʵ�֡�<br/>
	 * ����integerSubtraction("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ļ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ��������
	 */
	public String integerSubtraction (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		String oppoOperand2 = negation(operand2);   //oppoOperand2ֻ��operand��ȡ������δ��1��������Ҫ�ڽ�λ��ȥ�����ϡ��Ǹ�1
		String result = adder(operand1, oppoOperand2, '1', length);
		return result;
	}
	
	/**
	 * �����˷���ʹ��Booth�㷨ʵ�֣��ɵ���{@link #integerAddition(String, String, char, int) integerAddition}�ȷ�����<br/>
	 * ����integerMultiplication("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ĳ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ����˽�������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ����˽��
	 */
	public String integerMultiplication (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		String formal1 = operand1;
		String formal2 = operand2;
		
		for(int i = 0;i<length-operand1.length();i++){
			formal1 = operand1.charAt(0)+formal1;
		}
		
		for(int i = 0;i<length-operand2.length();i++){
			formal2 = operand2.charAt(0)+formal2;
		}
		
		String product = formal2+"0";
		
		for(int i = 0;i<length;i++)
			product = "0" + product; 
		
		for(int i = 0;i<length;i++){
			String part = product.substring(0,length);
			//�Ƚ�P0��P1��ֵ
			if(product.charAt(2*length)-product.charAt(2*length-1)==1){
				part = integerAddition(part,formal1,length).substring(1);
			}else if(product.charAt(2*length)-product.charAt(2*length-1)==-1){
				part = integerSubtraction(part,formal1,length).substring(1);
			}
			product = part+product.substring(length);
			product = ariRightShift(product,1);
		}
		
		String result = product.substring(length,2*length);
		
		for(int i =0;i<length;i++){
			if(product.charAt(i)!=result.charAt(0)){
				return "1"+result;
			}
		}
		return "0"+result;
	}
	
	/**
	 * �����Ĳ��ָ������������ɵ���{@link #integerAddition(String, String, char, int) integerAddition}�ȷ���ʵ�֡�<br/>
	 * ����integerDivision("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ĳ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊ2*length+1���ַ�����ʾ�������������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0�������lengthλΪ�̣����lengthλΪ����
	 */
	public String integerDivision (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		if(integerTrueValue(operand1)=="0"){
			String result = "";
			for(int i = 0;i<2*length+1;i++){
				result += "0";
			}
			return result;
		}else if(integerTrueValue(operand2)=="0"){
			
		}
		String rAndq = operand1;
		for(int i = 0;i<length*2-operand1.length();i++){
			rAndq = operand1.charAt(0)+rAndq;
		}//��չ��2nλ�����������Ĵ������̼Ĵ�����
//		System.out.println(rAndq);
		char overflow = '0';
		
		for(int i = 0;i<length;i++){
			//��������Ĵ����е��������������ͬ��������
			if(rAndq.charAt(0)==operand2.charAt(0)){
				rAndq = integerSubtraction(rAndq.substring(0,length), operand2, length).substring(1)+rAndq.substring(length);
			}else{
				rAndq = integerAddition(rAndq.substring(0,length), operand2, length).substring(1)+rAndq.substring(length);
			}
			
			if(rAndq.charAt(0)==operand2.charAt(0)){
				rAndq = rAndq.substring(1)+"1";
			}else{
				rAndq = rAndq.substring(1)+"0";				
			}
		}
		
//		System.out.println(rAndq);
		
		if(rAndq.charAt(0)==operand2.charAt(0)){
			rAndq = integerSubtraction(rAndq.substring(0,length), operand2, length).substring(1)+rAndq.substring(length);
		}else{
			rAndq = integerAddition(rAndq.substring(0,length), operand2, length).substring(1)+rAndq.substring(length);
		}
		
		String quotient = rAndq.substring(length);
		String reminder = rAndq.substring(0,length);		
		
		//�������ͱ��������Ų�ͬ�������������������ͬ������������ӷ�
		if(reminder.charAt(0)==operand2.charAt(0)){
			if(reminder.charAt(0)!=operand1.charAt(0)){
				reminder = integerSubtraction(reminder,operand2,length).substring(1);
			}
			quotient = quotient.substring(1)+"1";
		}else{
			if(reminder.charAt(0)!=operand1.charAt(0))
				reminder = integerAddition(reminder,operand2,length).substring(1);
			quotient = quotient.substring(1)+"0";
//			System.out.println(reminder);
		}
		
		//����̺ͳ��������෴���̼�1
		if(quotient.charAt(0)!=operand2.charAt(0)){	
			quotient = oneAdder(quotient).substring(1);
		}
		
//		System.out.println(quotient);

		//���λ+��+����
		return overflow+quotient+reminder;
	}
	
	/**
	 * �����������ӷ���Ҫ�����{@link #integerAddition(String, String, int) integerAddition}��{@link #integerSubtraction(String, String, int) integerSubtraction}�ȷ���ʵ�֡�
	 * �����ŵ�ȷ��������Ƿ���������Ҫ��������㷨���У�����ֱ��תΪ�����ʾ��������ת����<br/>
	 * ����signedAddition("1100", "1011", 8)
	 * @param operand1 ������ԭ���ʾ�ı����������е�1λΪ����λ
	 * @param operand2 ������ԭ���ʾ�ļ��������е�1λΪ����λ
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ����������ţ�����ĳ���������ĳ���С��lengthʱ����Ҫ���䳤����չ��length
	 * @return ����Ϊlength+2���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������2λΪ����λ����lengthλ����ӽ��
	 */
	public String signedAddition (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		String result = "";
		
		//��������Ϊ������ֱ�ӵ�����������
		if(operand1.charAt(0)=='0'&&operand2.charAt(0)=='0'){
			//lengthλ���޷������������������
			result = integerAddition(operand1,operand2,length);
			return "00"+result.substring(1);
		}
		
		//����һ���Ǹ������ڶ����������������ڶ���������һ����
		else if(operand1.charAt(0)=='1'&&operand2.charAt(0)=='0'){
			//lengthλ���޷���������������ȥ�������������
			result = integerSubtraction(operand2,"0"+operand1.substring(1),length);
			String num = result.substring(1);
			//������Ϊ������ȥ������λ��ȡ����һ
			if(result.charAt(0)=='1'){
				num = negation(num);
				num = oneAdder(num).substring(1);
			}
			return "0"+result.charAt(1)+num;
		}
		
		//����һ�����������ڶ����Ǹ�����������һ�������ڶ�����
		else if(operand1.charAt(0)=='0'&&operand2.charAt(0)=='1'){
			//lengthλ���޷���������������ȥ�������������
			result = integerSubtraction(operand1,"0"+operand2.substring(1),length);
			String num = result.substring(1);
			//������Ϊ������ȥ������λ��ȡ����һ
			if(result.charAt(0)=='1'){
				num = negation(num);
				num = oneAdder(num).substring(1);
			}
			return "0"+result.charAt(1)+num;
		}
		
		//���������Ǹ�����������һ�����ӵڶ�����������λΪ1
		else if(operand1.charAt(0)=='1'&&operand2.charAt(0)=='1'){
			//lengthλ���޷����������������������������
			result = integerAddition("0"+operand1.substring(1),"0"+operand2.substring(1),length);
			return "01"+result.substring(1);
		}
		return null;
	}
	
	/**
	 * �������ӷ����ɵ���{@link #signedAddition(String, String, int) signedAddition}�ȷ���ʵ�֡�<br/>
	 * ����floatAddition("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ļ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param gLength ����λ�ĳ���
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ����ӽ�������е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
	 */
	public String floatAddition (String operand1, String operand2, int eLength, int sLength, int gLength) {
		// TODO YOUR CODE HERE.
		String expo1 = operand1.substring(1,eLength+1);
		String expo2 = operand2.substring(1,eLength+1);
		char hide1 = '1';//����λ��ʼΪ1
		char hide2 = '1';
		if(Integer.parseInt(integerTrueValue(expo1))==0){
			hide1 = '0';
		}
		if(Integer.parseInt(integerTrueValue(expo2))==0){
			hide1 = '0';
		}
		
		String sign1 = hide1+operand1.substring(eLength+1);
		String sign2 = hide2+operand2.substring(eLength+1);
		String symbol1 = ""+operand1.charAt(0);
		String symbol2 = ""+operand2.charAt(0);
		
		//��չ������λ��sign�ĳ�������ΪsLength+gLength
		for(int i = 0;i<gLength;i++){
			sign1 +="0";
			sign2 +="0";
		}
		
		//�����һ����Ϊ����0����ô�𰸼�Ϊ��һ����
		if(floatTrueValue(operand1, eLength, sLength) == "0.0"||floatTrueValue(operand1, eLength, sLength)=="-0.0")
			return "0"+operand2;  // x == 0, return y
		else if(floatTrueValue(operand2, eLength, sLength) == "0.0"||floatTrueValue(operand2, eLength, sLength) == "-0.0")
			return "0"+operand1; // y == 0, return x
		
		if(expo1!=expo2){

			//���������1��ָ���Ȳ�����2�Ĵ󣬲�����2��β�����ơ�ָ����1
			if(Integer.parseInt(integerTrueValue("0"+expo1))>Integer.parseInt(integerTrueValue("0"+expo2))){
				while(Integer.parseInt(integerTrueValue("0"+expo1))-Integer.parseInt(integerTrueValue("0"+expo2))!=0){
					sign2 = logRightShift(sign2,1);
					//���������2��β�����0����Ϊ������1
					if(Integer.parseInt(sign2)==0){
						return "0"+operand1;
					}
					
					expo2 = oneAdder(expo2).substring(1);
				}
			}else{//���������2��ָ���Ȳ�����1�Ĵ󣬲�����1��β�����ơ�ָ����1
				while(Integer.parseInt(integerTrueValue("0"+expo2))-Integer.parseInt(integerTrueValue("0"+expo1))!=0){
					sign1 = logRightShift(sign1,1);
					//���������1��β�����0����Ϊ������2
					if(Integer.parseInt(integerTrueValue(sign1))==0){
						return "0"+operand2;
					}
					
					expo1 = oneAdder(expo1).substring(1);
				}
			}			
		}
		
		
		String expo = expo1;
		String significand = "";
		
//		System.out.println(sign2);
		//β����ӣ�lengthΪ4�ı���
		int length = ((2+sLength+gLength)/4+1)*4;
		//sum��1λ����λ��1+sLength+gLengthλ���޷���β��
		//sum����Ϊlength+2
		String sum = this.signedAddition(symbol1+sign1,symbol2+sign2 , length);
		String result = sum.substring(length-gLength-sLength,length+2);
		//����Ϊ2+sLength����һλ���������ڶ�λ����λ��ʣ��sLength+gLengthλ��β��
//		System.out.println(result);
		char symbol = sum.charAt(0);
		
		//������Ϊ0������0
		if(Integer.parseInt(integerTrueValue(result))==0){
			return "0"+integerRepresentation("0", 1+eLength+sLength);
		}
		
//		System.out.println(result);
		//���β��֮���������ôָ����1��β�����ƣ����ж�ָ���Ƿ����	
		if(result.charAt(0)!='0'){
			
			expo = oneAdder(expo);
//			System.out.println(expo);
			result = logRightShift(result, 1);
			//���ָ������������������
			if(expo.charAt(0)=='1'){
				return "1"+expo.substring(1)+significand.substring(1,sLength+1);
			}
			expo = expo.substring(1);
		}
		significand = result.substring(1);
		
		
		
		// ����λΪ 0����Ҫ����β��������λΪ 1
//		System.out.println(significand);
		if(significand.charAt(0) == '0'){
			
			int exponent = Integer.parseInt(integerTrueValue(expo));
			//������λΪ0ʱ��β�����ƣ�ָ����1
			while(significand.charAt(0)!='1'){
				significand = leftShift(significand, 1);
				exponent = exponent - 1;
				if(exponent==0){
					return "0"+integerRepresentation(Integer.toString(exponent), eLength)+significand.substring(1,sLength+1);
				}
				expo = integerRepresentation(Integer.toString(exponent),eLength);
			}
		}
		
		significand = significand.substring(1, sLength+1);

		return "0"+symbol+expo+significand;
	}
	
	/**
	 * �������������ɵ���{@link #floatAddition(String, String, int, int, int) floatAddition}����ʵ�֡�<br/>
	 * ����floatSubtraction("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ļ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param gLength ����λ�ĳ���
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ�������������е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
	 */
	public String floatSubtraction (String operand1, String operand2, int eLength, int sLength, int gLength) {
		// TODO YOUR CODE HERE.
		char symbol2 = (char) ('1'-operand2.charAt(0)+'0');
		operand2 = symbol2+operand2.substring(1);
		String result = floatAddition(operand1, operand2, eLength, sLength, gLength);
		return result;
	}
	
	/**
	 * �������˷����ɵ���{@link #integerMultiplication(String, String, int) integerMultiplication}�ȷ���ʵ�֡�<br/>
	 * ����floatMultiplication("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ĳ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ����˽��,���е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
	 */
	public String floatMultiplication (String operand1, String operand2, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		
		int expo1 = Integer.parseInt(integerTrueValue("0"+operand1.substring(1,1+eLength))); 
		int expo2 = Integer.parseInt(integerTrueValue("0"+operand2.substring(1,1+eLength)));
		int bias = (int)(Math.pow(2, eLength-1) - 1);
		
		char hide1 = '1';//����λ��ʼΪ1
		char hide2 = '1';
		if(expo1==0){
			hide1 = '0';
		}
		if(expo2==0){
			hide1 = '0';
		}
		
		String sign1 = hide1+operand1.substring(eLength+1);
		String sign2 = hide2+operand2.substring(eLength+1);
//		System.out.println(sign1);
		
		char symbol = '0';
		if(operand1.charAt(0)!=operand2.charAt(0)){
			symbol = '1';
		}
		
		//��������������Ƿ�Ϊ0������0�򷵻�0
		if(floatTrueValue(operand1, eLength, sLength) == "0.0"||floatTrueValue(operand1, eLength, sLength)=="-0.0"
				||floatTrueValue(operand2, eLength, sLength) == "0.0"||floatTrueValue(operand2, eLength, sLength) == "-0.0"){
			String result = "";
			for(int i = 0;i<1+eLength+sLength;i++){
				result += "0";
			}
			return symbol+result;
		}
		
		//ָ����ӣ���ȥƫֵ
		int exponent = expo1 + expo2 - bias;
		
		//���ָ�����磬��������
		if(exponent > Math.pow(2, eLength)-1){
			String expo = "";
			String sign = "";
			for(int i = 0;i<eLength;i++){
				expo += "1";
			}
			for(int i = 0;i<sLength;i++){
				sign += "0";
			}
			
			return "1"+symbol+expo+sign;
		}
		//ָ�����緵��0
		else if(exponent < 0){
			String symAndSign = "";
			for(int i = 0;i<eLength+sLength;i++){
				symAndSign += "0";
			}
			
			return "0"+symbol+symAndSign;
		}
		
		//β�����
		String significand = sign2;
		int length = ((1+sLength)/4+1)*4-1;
		//��������2��β����չΪ2*lengthλ
		for(int i = 0;i<2*length-sign2.length();i++){
			significand = "0"+significand;
		}
		//���
		for(int i = length-1;i>=0;i--){
			if(significand.charAt(2*length-1)=='1'){
				significand = integerAddition("0"+significand.substring(0, length), "0"+sign1, length+1).substring(2)+significand.substring(length);
			}
			
			significand = logRightShift(significand, 1);
//			System.out.println(significand);
		}
		
		significand = significand.substring(length-sLength+1,2*length);
		
		//����
		if(significand.charAt(0)=='1'){
			exponent ++;
			//���ָ�����磬��������
			if(exponent > Math.pow(2, eLength)-1){
				String expo = "";
				String sign = "";
				for(int i = 0;i<eLength;i++){
					expo += "1";
				}
				for(int i = 0;i<sLength;i++){
					sign += "0";
				}
				
				return "1"+symbol+expo+sign;
			}
		}	
		significand = leftShift(significand, 1);
		
		while(significand.charAt(0)!='1'){
			exponent --;
			significand = leftShift(significand, 1);
			if(exponent==0){
				String expo = integerRepresentation("0", eLength);
				String sign = significand.substring(1,1+sLength);
				return "0"+symbol+expo+sign;
			}
		}
//		System.out.println(significand);
		
		String expo = integerRepresentation(Integer.toString(exponent), eLength+1).substring(1);
		String sign = significand.substring(1,1+sLength);
		return "0"+symbol+expo+sign;

	}
	
	/**
	 * �������������ɵ���{@link #integerDivision(String, String, int) integerDivision}�ȷ���ʵ�֡�<br/>
	 * ����floatDivision("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ĳ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ����˽��,���е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
	 */
	public String floatDivision (String operand1, String operand2, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		int expo1 = Integer.parseInt(integerTrueValue("0"+operand1.substring(1,1+eLength))); 
		int expo2 = Integer.parseInt(integerTrueValue("0"+operand2.substring(1,1+eLength)));
		int bias = (int)(Math.pow(2, eLength-1) - 1);
		
		char hide1 = '1';//����λ��ʼΪ1
		char hide2 = '1';
		if(expo1==0){
			hide1 = '0';
		}
		if(expo2==0){
			hide1 = '0';
		}
		
		String sign1 = hide1+operand1.substring(eLength+1);
		String sign2 = hide2+operand2.substring(eLength+1);
//		System.out.println(sign1);
		
		char symbol = '0';
		if(operand1.charAt(0)!=operand2.charAt(0)){
			symbol = '1';
		}
		
		//���������Ϊ����0����ô�𰸼�Ϊ0
		if(floatTrueValue(operand1, eLength, sLength) == "0.0"||floatTrueValue(operand1, eLength, sLength)=="-0.0"){
			String result = "";
			for(int i = 0;i<1+eLength+sLength;i++){
				result += "0";
			}
			return "0"+symbol+result;
		}
		//�������Ϊ0����Ϊ����
		else if(floatTrueValue(operand2, eLength, sLength) == "0.0"||floatTrueValue(operand2, eLength, sLength) == "-0.0"){
			String expo = "";
			String sign = "";
			for(int i = 0;i<eLength;i++){
				expo += "1";
			}
			for(int i = 0;i<sLength;i++){
				sign += "0";
			}
			
			return "0"+symbol+expo+sign;
		}
		
		//ָ�����������ƫֵ
		int exponent = expo1 - expo2 + bias;
		
		//���ָ�����磬��������
		if(exponent > Math.pow(2, eLength)-1){
			String expo = "";
			String sign = "";
			for(int i = 0;i<eLength;i++){
				expo += "1";
			}
			for(int i = 0;i<sLength;i++){
				sign += "0";
			}
			
			return "1"+symbol+expo+sign;
		}
		//ָ�����緵��0
		else if(exponent < 0){
			String symAndSign = "";
			for(int i = 0;i<eLength+sLength;i++){
				symAndSign += "0";
			}
			
			return "0"+symbol+symAndSign;
		}
		
		String significand = sign1;
		int length = ((1+sLength)/4+1)*4-1;
		//�������
		for(int i = 0;i<length-sign1.length();i++){
			significand = "0"+significand;
		}
		
		for(int i = 0;i<length;i++){
			significand = significand+"0";
		}
		
//		System.out.println(significand);
		
		for(int i = 0;i<length;i++){
//			System.out.println(significand);
			String reminder = significand.substring(0, length);
			char c = '0';
			//����������ͼ�
			String temp = integerSubtraction("0"+reminder, "0"+sign2, length+1).substring(2);
			if(Integer.parseInt(integerTrueValue(temp))>=0){
				reminder = temp;
				c = '1';
			}
			significand = reminder+significand.substring(length, length*2);
			significand = significand.substring(1)+c;
			
		}
		
		significand = significand.substring(length,2*length);
//		System.out.println(significand);
		
		while(significand.charAt(0)!='1'){
			exponent --;
			significand = leftShift(significand, 1);
			if(exponent==0){
				String expo = integerRepresentation("0", eLength);
				String sign = significand.substring(1,1+sLength);
				return "0"+symbol+expo+sign;
			}
		}
		
		String expo = integerRepresentation(Integer.toString(exponent), eLength+1).substring(1);
		String sign = significand.substring(1,1+sLength);
		return "0"+symbol+expo+sign;
	}
}
