package license.des;


public class DES {
//初始置换表 IP	
int[] IP={58,50,42,34,26,18,10,2,
          60,52,44,36,28,20,12,4,
          62,54,46,38,30,22,14,6,
          64,56,48,40,32,24,16,8,
          57,49,41,33,25,17,9,1,
          59,51,43,35,27,19,11,3,
          61,53,45,37,29,21,13,5,
          63,55,47,39,31,23,15,7};
//逆初始置换表IP^-1  
int[] IP_1={40,8,48,16,56,24,64,32,
            39,7,47,15,55,23,63,31,
            38,6,46,14,54,22,62,30,
            37,5,45,13,53,21,61,29,
            36,4,44,12,52,20,60,28,
            35,3,43,11,51,19,59,27,
            34,2,42,10,50,18,58,26,
            33,1,41,9,49,17,57,25};

//密钥的置换函数PC_1
int[] PC_1={57,49,41,33,25,17,9,
           1,58,50,42,34,26,18,
           10,2,59,51,43,35,27,
           19,11,3,60,52,44,36,
           63,55,47,39,31,23,15,
           7,62,54,46,38,30,22,
           14,6,61,53,45,37,29,
           21,13,5,28,20,12,4};
//密钥的压缩置换函数PC_2
int[] PC_2={14,17,11,24,1,5,
            3,28,15,6,21,10,
            23,19,12,4,26,8,
            16,7,27,20,13,2,
            41,52,31,37,47,55,
            30,40,51,45,33,48,
            44,49,39,56,34,53,
            46,42,50,36,29,32};
//子密匙生成 每轮移动的位数 Move
int[] Move={1,1,2,2,2,2,2,2,
            1,2,2,2,2,2,2,1};

//扩展置换  Extern 32位拓展到48位
int[] Extern= {32,1,2,3,4,5,
               4,5,6,7,8,9,
               8,9,10,11,12,13,
               12,13,14,15,16,17,
               16,17,18,19,20,21,
               20,21,22,23,24,25,
               24,25,26,27,28,29,
               28,29,30,31,32,1};

//S盒 [8][4][16]
int[][][] S=//S1   
{{{14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7},   
  {0,15,7,4,14,2,13,1,10,6,12,11,9,5,3,8},   
    {4,1,14,8,13,6,2,11,15,12,9,7,3,10,5,0},   
    {15,12,8,2,4,9,1,7,5,11,3,14,10,0,6,13}},   
    //S2   
  {{15,1,8,14,6,11,3,4,9,7,2,13,12,0,5,10},   
  {3,13,4,7,15,2,8,14,12,0,1,10,6,9,11,5},   
  {0,14,7,11,10,4,13,1,5,8,12,6,9,3,2,15},   
  {13,8,10,1,3,15,4,2,11,6,7,12,0,5,14,9}},   
  //S3   
  {{10,0,9,14,6,3,15,5,1,13,12,7,11,4,2,8},   
  {13,7,0,9,3,4,6,10,2,8,5,14,12,11,15,1},   
    {13,6,4,9,8,15,3,0,11,1,2,12,5,10,14,7},   
  {1,10,13,0,6,9,8,7,4,15,14,3,11,5,2,12}},   
  //S4   
  {{7,13,14,3,0,6,9,10,1,2,8,5,11,12,4,15},   
  {13,8,11,5,6,15,0,3,4,7,2,12,1,10,14,9},   
  {10,6,9,0,12,11,7,13,15,1,3,14,5,2,8,4},   
  {3,15,0,6,10,1,13,8,9,4,5,11,12,7,2,14}},   
  //S5   
  {{2,12,4,1,7,10,11,6,8,5,3,15,13,0,14,9},   
  {14,11,2,12,4,7,13,1,5,0,15,10,3,9,8,6},   
  {4,2,1,11,10,13,7,8,15,9,12,5,6,3,0,14},   
  {11,8,12,7,1,14,2,13,6,15,0,9,10,4,5,3}},   
  //S6   
  {{12,1,10,15,9,2,6,8,0,13,3,4,14,7,5,11},   
  {10,15,4,2,7,12,9,5,6,1,13,14,0,11,3,8},   
  {9,14,15,5,2,8,12,3,7,0,4,10,1,13,11,6},   
  {4,3,2,12,9,5,15,10,11,14,1,7,6,0,8,13}},   
  //S7   
  {{4,11,2,14,15,0,8,13,3,12,9,7,5,10,6,1},   
  {13,0,11,7,4,9,1,10,14,3,5,12,2,15,8,6},   
  {1,4,11,13,12,3,7,14,10,15,6,8,0,5,9,2},   
  {6,11,13,8,1,4,10,7,9,5,0,15,14,2,3,12}},   
  //S8   
  {{13,2,8,4,6,15,11,1,10,9,3,14,5,0,12,7},   
  {1,15,13,8,10,3,7,4,12,5,6,11,0,14,9,2},   
  {7,11,4,1,9,12,14,2,0,6,10,13,15,3,5,8},   
  {2,1,14,7,4,10,8,13,15,12,9,0,3,5,6,11}}};   


//P盒置换
int[] P = {16,7,20,21,
           29,12,28,17,
           1,15,23,26,
           5,18,31,10,
           2,8,24,14,
           32,27,3,9,
           19,13,30,6,
           22,11,4,25};

public int[][] key16=new int[16][48];

public void ByteToBit(int[] Out,char[] In,int bits) {
	for(int i=0;i<bits;i++) {
		Out[i]=((In[i/8]>>(7-(i%8))) & 1);//一个字节（8位）的最低位作为变换后的第一位
	}
	
}

public void BitToByte(char[] Out,int[] In,int bits) {
	
	for(int i=0;i<bits/8;i++)
		Out[i]=0;
	for(int i=0;i<bits;i++)
		Out[i/8] |=(In[i] << (7-(i%8)));	
}


public void key16Creat(char[] key) { // 16轮密匙生成函数
	
  int[] keyInt=new int[64];//8字节的密钥化为64bit
  int[] key56=new int[56];//64bit的  密匙压缩为 56位
  ByteToBit(keyInt,key,64);
  for(int i=0;i<56;i++) {
	  key56[i]=keyInt[PC_1[i]-1];
  }
  for(int i=0;i<16;i++) {  //16轮密匙生成
	  int mov=0;
	  mov=Move[i]; //左移的位数
	  int tmp=0;
      for(int j=0;j<mov;j++) {
    
    	  /*
    	  for(int k=0;k<8;k++) {
    		   tmp=key56[k*7];
    		  for(int l=k*7;l<k*7+7;l++) {
    			  key56[l]=key56[l+1];
    		  }
    		  key56[k*7+6]=tmp;
    	  }
    	  */
    	  tmp=key56[0];
    	  for(int k=0;k<27;k++) {
    		  key56[k]=key56[k+1];
    	  }
    	  key56[27]=tmp;
    	  tmp=key56[28];
    	  for(int k=28;k<55;k++) {
    		  key56[k]=key56[k+1];
    	  }
    	  key56[55]=tmp;
      }
      for(int j=0;j<48;j++) { //压缩置换
      key16[i][j]=key56[PC_2[j]-1];
      }
  }
  
}

public void xor(int[] input, int[] key,int len) { //异或函数
	for(int i=0;i<len;i++) 
		input[i]=input[i]^key[i];	
}

public  char[] Des(char[] input,int type) { //加解密
	char[] result=new char[8];
	int[] inputInt=new int[64];
	int[] inputIntIP=new int[64];
	int[] inputIntIP_1=new int[64];
	int[] key=new int[48];
	int[] schange=new int[32];
    int[] tmp=new int[32];
	ByteToBit(inputInt,input,64); //明文64bit
	
	for(int i=0;i<64;i++) {//IP置换
		inputIntIP[i]=inputInt[IP[i]-1];
	}
	int[] left=new int[32]; //左半部分
	int[] right=new int[32];//右半部分
	int[] rightExt=new int[48];//右半部分扩展
	for(int i=0;i<32;i++)
		left[i]=inputIntIP[i];
	for(int i=0;i<32;i++)
		right[i]=inputIntIP[i+32];
	for(int i=0;i<48;i++)
		rightExt[i]=right[Extern[i]-1];
	

	int[][] keyarr=new int[16][48];
	if(type==0) {// 加密
		for(int i=0;i<16;i++)
			for(int j=0;j<48;j++)
				keyarr[i][j]=key16[i][j];
	}
	if(type==1)	{//解密
		for(int i=0;i<16;i++)
			for(int j=0;j<48;j++)
				keyarr[i][j]=key16[15-i][j];
	}
	
	   for(int i=0;i<16;i++) { //16次轮密码
	  
		   for(int j=0;j<32;j++) { //保存右半部分
			   tmp[j]=right[j];
		   }		   
		   for(int j=0;j<48;j++) { //密匙
			//   key[j]=key16[i][j];
			   key[j]=keyarr[i][j];
		   }
		   for(int j=0;j<48;j++) { //右半部分扩展32-48
			   rightExt[j]=right[Extern[j]-1];
		   }
		   
		   xor(rightExt,key,48); //异或
		   
		   //S盒
		   for(int j=0;j<8;j++) {
			   int row=rightExt[j*6]*2+rightExt[j*6+5]; //获得S盒的行和列的值
			   int col=rightExt[j*6+1]*8+rightExt[j*6+2]*4+rightExt[j*6+3]*2+rightExt[j*6+4]; 
			   schange[j*4]=(S[j][row][col]/8)%2;
			   schange[j*4+1]=(S[j][row][col/4])%2;
			   schange[j*4+2]=(S[j][row][col]/2)%2;
			   schange[j*4+3]=S[j][row][col]%2;
		   }
		   
		   for(int j=0;j<32;j++) {//p置换
			   right[j]=schange[P[j]-1];
		   }
		   
		   xor(right,left,32); //异或
		   
		   //交换左右,右边的存到左边
		   for(int j=0;j<32;j++) {
			   left[j]=tmp[j];
		   }
	   }
	   
	   
	   //最后左右互换，这个可以没有
	  for(int j=0;j<32;j++) {
        tmp[j]=left[j];
	    left[j]=right[j];
	    right[j]=tmp[j];		 
	  }  
	  
	  //左右结合
	  for(int i=0;i<32;i++)
		  inputIntIP[i]=left[i];
	  for(int i=0;i<32;i++)
		  inputIntIP[i+32]=right[i];
	  
	  //IP逆置换
	  for(int i=0;i<64;i++)
		  inputIntIP_1[i]=inputIntIP[IP_1[i]-1];
	  //位到字节的转换
	  BitToByte(result,inputIntIP_1,64);

    return result;
}

public static void main(String[] args) {
	char[] key={'1','2','3','4','5','6','7','8'};
	//char[] key={'8','7','6','5','4','3','2','1'};
	System.out.print("输入密钥:");
	for(int i=0;i<8;i++)
		System.out.print(key[i]);
	System.out.println("");
	
	char[] input=new char[8];
	char[] enc_result=new char[8];
	char[] decresult=new char[8];
	for(int i=0;i<input.length;i++) {
		input[i]=(char)('7'-i);
	}
	DES des=new DES();
   
	long t1=System.nanoTime();
	des.key16Creat(key);//生成密钥
	t1=System.nanoTime()-t1;
	
	long t2=System.nanoTime();
	enc_result=des.Des(input,0); //加密
	t2=System.nanoTime()-t2;
	
	long t3=System.nanoTime();
	decresult=des.Des(enc_result, 1);//解密
	t3=System.nanoTime()-t3;
	
	
	for(int i=0;i<16;i++){
		System.out.print("第"+(i+1)+"轮密钥:");
		for(int j=0;j<46;j++){
			//for(int k=0;k<6;k++)
		System.out.print(des.key16[i][j]+",");
		}
		System.out.println("");
	}	
	
	System.out.print("加密明文:");
	for(int i=0;i<8;i++)
		System.out.print(input[i]);
	System.out.println("");
	System.out.print("加密密文:");
	for(int i=0;i<8;i++)
		System.out.print(enc_result[i]);
	System.out.println("");
	System.out.print("解密密文:");
	for(int i=0;i<8;i++)
		System.out.print(decresult[i]);
	System.out.println("");
	System.out.println("密钥生成时间:"+(t1)+"ns");
	System.out.println("加密时间:"+(t2)+"ns");
	System.out.println("解密时间:"+(t3)+"ns");
//	System.out.println(c);
	
}
}