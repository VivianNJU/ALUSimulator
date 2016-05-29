/**
 * 模拟ALU进行整数和浮点数的四则运算
 * @151250160_吴静琦[请将此处修改为“学号_姓名”]
 *
 */

public class ALU {

	/**
	 * 生成十进制整数的二进制补码表示。<br/>
	 * 例：integerRepresentation("9", 8)
	 * @param number 十进制整数。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 * @param length 二进制补码表示的长度
	 * @return number的二进制补码表示，长度为length
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
	 * 生成十进制浮点数的二进制表示。
	 * 需要考虑 0、反规格化、正负无穷（“+Inf”和“-Inf”）、 NaN等因素，具体借鉴 IEEE 754。
	 * 舍入策略为向0舍入。<br/>
	 * 例：floatRepresentation("11.375", 8, 11)
	 * @param number 十进制浮点数，包含小数点。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return number的二进制表示，长度为 1+eLength+sLength。从左向右，依次为符号、指数（移码表示）、尾数（首位隐藏）
	 */
	public String floatRepresentation (String number, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		Double num = Double.parseDouble(number);
		int half = (int)(Math.pow(2, eLength-1)-1);
		
		int integ = (int)Math.abs(num);
		Double doub = Math.abs(num)-integ;//全部为正
		//比较是否超出可以表示的最大值
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
		}//零的表示方法
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
		
		String symbol;//symbol是符号位。0代表非负数，1代表负数
		if(num>=0)
			symbol = "0";
		else
			symbol = "1";
		
		String integStr = "";//算出整数部分的二进制表示
		while(integ!=0){
			if(integ%2==1){
				integStr = "1"+integStr;
			}else{
				integStr = "0"+integStr;
			}
			
			integ = integ/2;
		}
		integ = (int)Math.abs(num);//重新初始化
		
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
		doub = Math.abs(num)-integ;//重新初始化
		
		//反规则化数的表示
		if(Math.abs(num)<Math.pow(2, -half+1)){
			String eTemp = "";
			
			for(int i=0;i<eLength;i++){
				eTemp += "0";
			}
			String sTemp = doubStr.substring(half-1);
			
			return symbol+eTemp+sTemp;
		}
		//整数部分二进制表示的长度减1加上half就是指数部分表示的数
		if(integ>0){
			String eTemp = integerRepresentation(Integer.toString(integStr.length()-1+half), eLength);
			
			String sTemp = integStr.substring(1)+doubStr;
			
			sTemp = sTemp.substring(0, sLength);
			
			return symbol+eTemp+sTemp;
		}
		//小数部分的1，左移
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
	 * 生成十进制浮点数的IEEE 754表示，要求调用{@link #floatRepresentation(String, int, int) floatRepresentation}实现。<br/>
	 * 例：ieee754("11.375", 32)
	 * @param number 十进制浮点数，包含小数点。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 * @param length 二进制表示的长度，为32或64
	 * @return number的IEEE 754表示，长度为length。从左向右，依次为符号、指数（移码表示）、尾数（首位隐藏）
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
	 * 计算二进制补码表示的整数的真值。<br/>
	 * 例：integerTrueValue("00001001")
	 * @param operand 二进制补码表示的操作数
	 * @return operand的真值。若为负数；则第一位为“-”；若为正数或 0，则无符号位
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
	 * 计算二进制原码表示的浮点数的真值。<br/>
	 * 例：floatTrueValue("01000001001101100000", 8, 11)
	 * @param operand 二进制表示的操作数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return operand的真值。若为负数；则第一位为“-”；若为正数或 0，则无符号位。正负无穷分别表示为“+Inf”和“-Inf”， NaN表示为“NaN”
	 */
	public String floatTrueValue (String operand, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		char c = operand.charAt(0);
		String symbol = "";
		if(c=='1')
			symbol = "-";
		
		//无符号位
		String exponentStr = operand.substring(1,eLength+1);
		int exponent = 0;
		for(int i=0;i<exponentStr.length();i++)
			exponent = exponent * 2 + (exponentStr.charAt(i)-'0'); 
		
		// 求出尾数位及其表示的值
		String significandStr = operand.substring(eLength+1,eLength+sLength+1);
		int significandResult = 0;
		for(int i=0;i<significandStr.length();i++)
			significandResult = significandResult * 2 + (significandStr.charAt(i)-48);
		double f = significandResult / Math.pow(2, sLength);
		
		if(exponent==0){
			// 正负0的情况
			if(f == 0.0){
				return symbol + "0";
			}
			// 正负反规格化数的情况
			else{
				int exponentReal = (int)(exponent - (Math.pow(2, eLength-1)-1));
				return symbol + (f * Math.pow(2, exponentReal));
			}
		}
		
		else if(exponent==Math.pow(2, eLength)-1){
			// 6.3.2（1）正负无穷大的情况
			if(f == 0.0){
				if(symbol.equals("-"))
					return "-Inf";
				else
					return "+Inf";//必须有正号
			}
			//6.3.2（2）非数或通知式非数的情况
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
	 * 按位取反操作。<br/>
	 * 例：negation("00001001")
	 * @param operand 二进制表示的操作数
	 * @return operand按位取反的结果
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
	 * 左移操作。<br/>
	 * 例：leftShift("00001001", 2)
	 * @param operand 二进制表示的操作数
	 * @param n 左移的位数
	 * @return operand左移n位的结果
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
	 * 逻辑右移操作。<br/>
	 * 例：logRightShift("11110110", 2)
	 * @param operand 二进制表示的操作数
	 * @param n 右移的位数
	 * @return operand逻辑右移n位的结果
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
	 * 算术右移操作。<br/>
	 * 例：logRightShift("11110110", 2)
	 * @param operand 二进制表示的操作数
	 * @param n 右移的位数
	 * @return operand算术右移n位的结果
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
	 * 全加器，对两位以及进位进行加法运算。<br/>
	 * 例：fullAdder('1', '1', '0')
	 * @param x 被加数的某一位，取0或1
	 * @param y 加数的某一位，取0或1
	 * @param c 低位对当前位的进位，取0或1
	 * @return 相加的结果，用长度为2的字符串表示，第1位表示进位，第2位表示和
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
	 * 4位先行进位加法器。要求采用{@link #fullAdder(char, char, char) fullAdder}来实现<br/>
	 * 例：claAdder("1001", "0001", '1')
	 * @param operand1 4位二进制表示的被加数
	 * @param operand2 4位二进制表示的加数
	 * @param c 低位对当前位的进位，取0或1
	 * @return 长度为5的字符串表示的计算结果，其中第1位是最高位进位，后4位是相加结果，其中进位不可以由循环获得
	 */
	public String claAdder (String operand1, String operand2, char c) {
		// TODO YOUR CODE HERE.
		String result = "";
		int[] P = new int[4];
		int[] G = new int[4];
		int[] C = new int[5];
		for(int i =0;i<operand1.length();i++){
			//算出Pi
			P[i] = fullAdder(operand1.charAt(operand1.length()-1-i),operand2.charAt(operand1.length()-1-i),'1').charAt(0)-'0';
			//System.out.println(P[i]);
			//算出Gi
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
	 * 加一器，实现操作数加1的运算。
	 * 需要采用与门、或门、异或门等模拟，
	 * 不可以直接调用{@link #fullAdder(char, char, char) fullAdder}、
	 * {@link #claAdder(String, String, char) claAdder}、
	 * {@link #adder(String, String, char, int) adder}、
	 * {@link #integerAddition(String, String, int) integerAddition}方法。<br/>
	 * 例：oneAdder("00001001")
	 * @param operand 二进制补码表示的操作数
	 * @return operand加1的结果，长度为operand的长度加1，其中第1位指示是否溢出（溢出为1，否则为0），其余位为相加结果
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
	 * 加法器，要求调用{@link #claAdder(String, String, char)}方法实现。<br/>
	 * 例：adder("0100", "0011", ‘0’, 8)
	 * @param operand1 二进制补码表示的被加数
	 * @param operand2 二进制补码表示的加数
	 * @param c 最低位进位
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相加结果
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
	 * 整数加法，要求调用{@link #adder(String, String, char, int) adder}方法实现。<br/>
	 * 例：integerAddition("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被加数
	 * @param operand2 二进制补码表示的加数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相加结果
	 */
	public String integerAddition (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		return adder(operand1,operand2,'0',length);
		
	}
	
	/**
	 * 整数减法，可调用{@link #integerAddition(String, String, char, int) integerAddition}方法实现。<br/>
	 * 例：integerSubtraction("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被减数
	 * @param operand2 二进制补码表示的减数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相减结果
	 */
	public String integerSubtraction (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		String oppoOperand2 = negation(operand2);   //oppoOperand2只是operand的取反，还未加1，所以需要在进位中去“加上”那个1
		String result = adder(operand1, oppoOperand2, '1', length);
		return result;
	}
	
	/**
	 * 整数乘法，使用Booth算法实现，可调用{@link #integerAddition(String, String, char, int) integerAddition}等方法。<br/>
	 * 例：integerMultiplication("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被乘数
	 * @param operand2 二进制补码表示的乘数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的相乘结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相乘结果
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
			//比较P0和P1的值
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
	 * 整数的不恢复余数除法，可调用{@link #integerAddition(String, String, char, int) integerAddition}等方法实现。<br/>
	 * 例：integerDivision("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被除数
	 * @param operand2 二进制补码表示的除数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为2*length+1的字符串表示的相除结果，其中第1位指示是否溢出（溢出为1，否则为0），其后length位为商，最后length位为余数
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
		}//扩展出2n位，存在余数寄存器和商寄存器中
//		System.out.println(rAndq);
		char overflow = '0';
		
		for(int i = 0;i<length;i++){
			//如果余数寄存器中的数与除数符号相同，做减法
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
		
		//若余数和被除数符号不同，当余数与除数符号相同，减法，否则加法
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
		
		//如果商和除数符号相反，商加1
		if(quotient.charAt(0)!=operand2.charAt(0)){	
			quotient = oneAdder(quotient).substring(1);
		}
		
//		System.out.println(quotient);

		//溢出位+商+余数
		return overflow+quotient+reminder;
	}
	
	/**
	 * 带符号整数加法，要求调用{@link #integerAddition(String, String, int) integerAddition}、{@link #integerSubtraction(String, String, int) integerSubtraction}等方法实现。
	 * 但符号的确定、结果是否修正等需要按照相关算法进行，不能直接转为补码表示后运算再转回来<br/>
	 * 例：signedAddition("1100", "1011", 8)
	 * @param operand1 二进制原码表示的被加数，其中第1位为符号位
	 * @param operand2 二进制原码表示的加数，其中第1位为符号位
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度（不包含符号），当某个操作数的长度小于length时，需要将其长度扩展到length
	 * @return 长度为length+2的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），第2位为符号位，后length位是相加结果
	 */
	public String signedAddition (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		String result = "";
		
		//若两个都为正数，直接当做补码运算
		if(operand1.charAt(0)=='0'&&operand2.charAt(0)=='0'){
			//length位的无符号整数，不可能溢出
			result = integerAddition(operand1,operand2,length);
			return "00"+result.substring(1);
		}
		
		//若第一个是负数，第二个是正数，当做第二个数减第一个数
		else if(operand1.charAt(0)=='1'&&operand2.charAt(0)=='0'){
			//length位的无符号整数，正数减去正数不可能溢出
			result = integerSubtraction(operand2,"0"+operand1.substring(1),length);
			String num = result.substring(1);
			//如果结果为负数，去除符号位，取反加一
			if(result.charAt(0)=='1'){
				num = negation(num);
				num = oneAdder(num).substring(1);
			}
			return "0"+result.charAt(1)+num;
		}
		
		//若第一个是正数，第二个是负数，当做第一个数减第二个数
		else if(operand1.charAt(0)=='0'&&operand2.charAt(0)=='1'){
			//length位的无符号整数，正数减去正数不可能溢出
			result = integerSubtraction(operand1,"0"+operand2.substring(1),length);
			String num = result.substring(1);
			//如果结果为负数，去除符号位，取反加一
			if(result.charAt(0)=='1'){
				num = negation(num);
				num = oneAdder(num).substring(1);
			}
			return "0"+result.charAt(1)+num;
		}
		
		//若两个都是负数，当做第一个数加第二个数，符号位为1
		else if(operand1.charAt(0)=='1'&&operand2.charAt(0)=='1'){
			//length位的无符号整数，正数加正数不可能溢出
			result = integerAddition("0"+operand1.substring(1),"0"+operand2.substring(1),length);
			return "01"+result.substring(1);
		}
		return null;
	}
	
	/**
	 * 浮点数加法，可调用{@link #signedAddition(String, String, int) signedAddition}等方法实现。<br/>
	 * 例：floatAddition("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 二进制表示的被加数
	 * @param operand2 二进制表示的加数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @param gLength 保护位的长度
	 * @return 长度为2+eLength+sLength的字符串表示的相加结果，其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatAddition (String operand1, String operand2, int eLength, int sLength, int gLength) {
		// TODO YOUR CODE HERE.
		String expo1 = operand1.substring(1,eLength+1);
		String expo2 = operand2.substring(1,eLength+1);
		char hide1 = '1';//隐藏位初始为1
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
		
		//扩展出保护位，sign的长度现在为sLength+gLength
		for(int i = 0;i<gLength;i++){
			sign1 +="0";
			sign2 +="0";
		}
		
		//如果有一个数为正负0，那么答案即为另一个数
		if(floatTrueValue(operand1, eLength, sLength) == "0.0"||floatTrueValue(operand1, eLength, sLength)=="-0.0")
			return "0"+operand2;  // x == 0, return y
		else if(floatTrueValue(operand2, eLength, sLength) == "0.0"||floatTrueValue(operand2, eLength, sLength) == "-0.0")
			return "0"+operand1; // y == 0, return x
		
		if(expo1!=expo2){

			//如果操作数1的指数比操作数2的大，操作数2的尾数右移、指数加1
			if(Integer.parseInt(integerTrueValue("0"+expo1))>Integer.parseInt(integerTrueValue("0"+expo2))){
				while(Integer.parseInt(integerTrueValue("0"+expo1))-Integer.parseInt(integerTrueValue("0"+expo2))!=0){
					sign2 = logRightShift(sign2,1);
					//如果操作数2的尾数变成0，答案为操作数1
					if(Integer.parseInt(sign2)==0){
						return "0"+operand1;
					}
					
					expo2 = oneAdder(expo2).substring(1);
				}
			}else{//如果操作数2的指数比操作数1的大，操作数1的尾数右移、指数加1
				while(Integer.parseInt(integerTrueValue("0"+expo2))-Integer.parseInt(integerTrueValue("0"+expo1))!=0){
					sign1 = logRightShift(sign1,1);
					//如果操作数1的尾数变成0，答案为操作数2
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
		//尾数相加，length为4的倍数
		int length = ((2+sLength+gLength)/4+1)*4;
		//sum是1位符号位，1+sLength+gLength位的无符号尾数
		//sum长度为length+2
		String sum = this.signedAddition(symbol1+sign1,symbol2+sign2 , length);
		String result = sum.substring(length-gLength-sLength,length+2);
		//长度为2+sLength，第一位检查溢出，第二位隐藏位，剩余sLength+gLength位是尾数
//		System.out.println(result);
		char symbol = sum.charAt(0);
		
		//如果结果为0，返回0
		if(Integer.parseInt(integerTrueValue(result))==0){
			return "0"+integerRepresentation("0", 1+eLength+sLength);
		}
		
//		System.out.println(result);
		//如果尾数之和溢出，那么指数加1，尾数右移，并判断指数是否溢出	
		if(result.charAt(0)!='0'){
			
			expo = oneAdder(expo);
//			System.out.println(expo);
			result = logRightShift(result, 1);
			//如果指数溢出，返回溢出报告
			if(expo.charAt(0)=='1'){
				return "1"+expo.substring(1)+significand.substring(1,sLength+1);
			}
			expo = expo.substring(1);
		}
		significand = result.substring(1);
		
		
		
		// 隐藏位为 0，需要左移尾数至隐藏位为 1
//		System.out.println(significand);
		if(significand.charAt(0) == '0'){
			
			int exponent = Integer.parseInt(integerTrueValue(expo));
			//当隐藏位为0时，尾数左移，指数减1
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
	 * 浮点数减法，可调用{@link #floatAddition(String, String, int, int, int) floatAddition}方法实现。<br/>
	 * 例：floatSubtraction("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 二进制表示的被减数
	 * @param operand2 二进制表示的减数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @param gLength 保护位的长度
	 * @return 长度为2+eLength+sLength的字符串表示的相减结果，其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatSubtraction (String operand1, String operand2, int eLength, int sLength, int gLength) {
		// TODO YOUR CODE HERE.
		char symbol2 = (char) ('1'-operand2.charAt(0)+'0');
		operand2 = symbol2+operand2.substring(1);
		String result = floatAddition(operand1, operand2, eLength, sLength, gLength);
		return result;
	}
	
	/**
	 * 浮点数乘法，可调用{@link #integerMultiplication(String, String, int) integerMultiplication}等方法实现。<br/>
	 * 例：floatMultiplication("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 二进制表示的被乘数
	 * @param operand2 二进制表示的乘数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return 长度为2+eLength+sLength的字符串表示的相乘结果,其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatMultiplication (String operand1, String operand2, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		
		int expo1 = Integer.parseInt(integerTrueValue("0"+operand1.substring(1,1+eLength))); 
		int expo2 = Integer.parseInt(integerTrueValue("0"+operand2.substring(1,1+eLength)));
		int bias = (int)(Math.pow(2, eLength-1) - 1);
		
		char hide1 = '1';//隐藏位初始为1
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
		
		//检查两个操作数是否为0，若有0则返回0
		if(floatTrueValue(operand1, eLength, sLength) == "0.0"||floatTrueValue(operand1, eLength, sLength)=="-0.0"
				||floatTrueValue(operand2, eLength, sLength) == "0.0"||floatTrueValue(operand2, eLength, sLength) == "-0.0"){
			String result = "";
			for(int i = 0;i<1+eLength+sLength;i++){
				result += "0";
			}
			return symbol+result;
		}
		
		//指数相加，减去偏值
		int exponent = expo1 + expo2 - bias;
		
		//如果指数上溢，返回无穷
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
		//指数下溢返回0
		else if(exponent < 0){
			String symAndSign = "";
			for(int i = 0;i<eLength+sLength;i++){
				symAndSign += "0";
			}
			
			return "0"+symbol+symAndSign;
		}
		
		//尾数相乘
		String significand = sign2;
		int length = ((1+sLength)/4+1)*4-1;
		//将操作数2的尾数扩展为2*length位
		for(int i = 0;i<2*length-sign2.length();i++){
			significand = "0"+significand;
		}
		//相乘
		for(int i = length-1;i>=0;i--){
			if(significand.charAt(2*length-1)=='1'){
				significand = integerAddition("0"+significand.substring(0, length), "0"+sign1, length+1).substring(2)+significand.substring(length);
			}
			
			significand = logRightShift(significand, 1);
//			System.out.println(significand);
		}
		
		significand = significand.substring(length-sLength+1,2*length);
		
		//规则化
		if(significand.charAt(0)=='1'){
			exponent ++;
			//如果指数上溢，报告无穷
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
	 * 浮点数除法，可调用{@link #integerDivision(String, String, int) integerDivision}等方法实现。<br/>
	 * 例：floatDivision("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 二进制表示的被除数
	 * @param operand2 二进制表示的除数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return 长度为2+eLength+sLength的字符串表示的相乘结果,其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatDivision (String operand1, String operand2, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		int expo1 = Integer.parseInt(integerTrueValue("0"+operand1.substring(1,1+eLength))); 
		int expo2 = Integer.parseInt(integerTrueValue("0"+operand2.substring(1,1+eLength)));
		int bias = (int)(Math.pow(2, eLength-1) - 1);
		
		char hide1 = '1';//隐藏位初始为1
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
		
		//如果被除数为正负0，那么答案即为0
		if(floatTrueValue(operand1, eLength, sLength) == "0.0"||floatTrueValue(operand1, eLength, sLength)=="-0.0"){
			String result = "";
			for(int i = 0;i<1+eLength+sLength;i++){
				result += "0";
			}
			return "0"+symbol+result;
		}
		//如果除数为0，答案为无穷
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
		
		//指数相减，加上偏值
		int exponent = expo1 - expo2 + bias;
		
		//如果指数上溢，返回无穷
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
		//指数下溢返回0
		else if(exponent < 0){
			String symAndSign = "";
			for(int i = 0;i<eLength+sLength;i++){
				symAndSign += "0";
			}
			
			return "0"+symbol+symAndSign;
		}
		
		String significand = sign1;
		int length = ((1+sLength)/4+1)*4-1;
		//两数相除
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
			//如果够减，就减
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
