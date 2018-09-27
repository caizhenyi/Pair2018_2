
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class MathExam {
	static int num1,k=0;//k ���õȼ�
	static StringBuffer word,id;
	static OutputStream out;
	static PrintWriter p;
	static int n,g,f,test,rem,grade;

	static int[] str= {0,0,1,1}; // 0Ϊ+-��1Ϊ*/
	static int[] answer = new int[n]; //��𰸵�����
	static int[] sub = new int[6];//�����ֵ�����
	static int[] remb = new int[n];
	static Collection<String> list=new ArrayList<String>();

	public static int[] input(String[] args,int[] str) {
		String regex="\\d*";
		if(args.length!=4) {//����������Ϊ���
			System.out.println("����Ĳ���ӦΪ4��");
			str[0]=0;
			return str;
		}
		if(!args[1].matches(regex) || !args[3].matches(regex)) {//���������벻Ϊ����
			System.out.println("������꼶��������ӦΪ����");
			str[0]=0;
			return str;
			}
		if(!((args[0].equals("-n") && args[2].equals("-grade"))||(args[0].equals("-grade") && args[2].equals("-n")))) {
			System.out.println("���Ҳ���-n����-grade");
			str[0]=0;
			return str;
		}
		String s0=String.valueOf(args[1]);
		String s1=String.valueOf(args[3]);
		grade=Integer.parseInt(s0);
		n=Integer.parseInt(s1);
		
		if(args[0].equals("-n") && args[2].equals("-grade")) {
		n=Integer.parseInt(s0);
		grade=Integer.parseInt(s1);
		}
		if( grade>3 || grade<=0 ) {
			System.out.println("�����꼶Ӧ��һ�����꼶");
			str[0]=0;
			return str;
		}
		if(n<=0||n>1000)
		{
			System.out.println("������Ŀ����Ӧ����1~1000");
			str[0]=0;
			return str;
		}
		str[0]=1;
		str[1]=n;
		str[2]=grade;
		return str;
	}
	
	
	//�ӷ�
	private static void add(int i,StringBuffer word,int[] answer, int grade,int[] sub, int m) {
		// TODO �Զ����ɵķ������
		if(grade==1) {
			num1 = (int) (Math.random()*(answer[i]-1));
			sub[m]=num1;
		}else {
			num1 = (int) (Math.random()*99);
			sub[m]=num1;
		}
		word.insert(word.length()," + "+num1);
		answer[i] = answer[i]+num1;
	}
	//����
	private static void sub(int i,StringBuffer word, int[] answer,int[] sub, int m) {
		// TODO �Զ����ɵķ������
		num1 = (int) (Math.random()*(answer[i]-1));
		sub[m]=num1;
		word.insert(word.length()," - "+num1);
		answer[i] = answer[i]-num1;
	}
	//�˷�
	private static void mul(int i,StringBuffer word, int[] answer, int grade,int[] sub, int m){
		// TODO �Զ����ɵķ������
		if(grade==2) {
			num1 = (int) (Math.random()*9);
			sub[m]=num1;
		}
		else {
			num1 = (int) (Math.random()*99);
			sub[m]=num1;
		}
		word.insert(word.length(), " x "+num1);
		answer[i]= answer[i]*num1;
	}
	//����
	private static String div(int i,int j,int f, StringBuffer word, int[] answer,int grade,int[] sub, int m, int[] remb, int k) {
		// TODO �Զ����ɵķ�����
		if(grade==2) {
			num1 = 1+(int)(Math.random()*9);
			rem=answer[i]%num1;
			remb[k] = rem;
			sub[m]=num1;
			word.insert(word.length()," �� "+num1);
			answer[i]=answer[i]/num1;
		}
		else{
			test = 1+(int)(Math.random()*2);
			if(answer[i]==0 && f-j<=2) return "false";
			if((test==2 || answer[i]==0) && (f-j>2)) {
				
				num1 = 1+(int)(Math.random()*(answer[i]-1));  //����
				if(answer[i]>99) {num1 = 1+(int)(Math.random()*99);}
				rem=answer[i]%num1;	
				sub[m]=num1;
				
				if(rem>0) {word.insert(0, "(");
					word.insert(word.length(), " - "+rem+") �� "+num1);
					answer[i]=(answer[i]-rem)/num1;
				}else {
					word.insert(word.length()," �� "+num1);
					answer[i]=answer[i]/num1;}
				return "j++";
			}
			
				num1 = (int)(Math.random()*((1000/answer[i])+1));  //������
				sub[m]=num1*answer[i];m++;
				word.insert(0,(num1*answer[i])+" �� ");
				answer[i] = num1;
			return "true";

		
	}
		return null;
	}
	
	
	
	private static void operation(int n, int grade)  {
		// TODO �Զ����ɵķ������
		//str���鴢����������ĵȼ��ͷ��ţ�answer�������ʽ�Ӵ𰸣�word���鴢����Ŀ��f�����������ĸ���;num������ĵ�һ����;g��ѡ���g�������;i�ǵ�i���⣻j�ǵ�ǰ��������
		File file = new File("out.txt"); 
		try {
			p = new PrintWriter(new FileOutputStream(file.getAbsolutePath()));
			out = new FileOutputStream(file);
			id = new StringBuffer("");
			
			for(int i=0;i<n;i++) {//��n����Ŀ��ѭ��
				int m=0; //�����ָ���
				int level=1;
				if(grade==1) {
					g = (int)(Math.random()*(str.length-2));
					answer[i]=(int) (Math.random()*99);
					sub[m]=answer[i];m++;
					word= new StringBuffer(answer[i]+"");
					if(g==1) {
						add(i,word,answer,g,sub,m);m++;
					}
					else {
						sub(i,word, answer,sub,m);m++;
					}
					
				}
				else if(grade==2) {
					g = (str.length-2)+(int)(Math.random()*str.length);
					if(g==3) {
						answer[i]=(int) (Math.random()*99);
						sub[m]=answer[i];m++;
						word= new StringBuffer(answer[i]+"");
						div(i,0,0,word, answer,grade,sub,m,remb,k);
						m++;k++;
					}
					else {
						answer[i]=(int) (Math.random()*9);	
						sub[m]=answer[i];m++;	
						word= new StringBuffer(answer[i]+"");
						mul(i,word, answer,grade,sub,m);
						m++;
					}
				}
				else {//���꼶
					f = 2+(int) (Math.random()*3); //f:�����ȡ������ĸ���
					g = (int)(Math.random()*(str.length-1));//��һ�������
					answer[i]= (int) (Math.random()*99);
					sub[m]=answer[i];m++;
					word= new StringBuffer(answer[i]+"");
					final int g1=g;
					for(int j=0;j<f;j++) {
						if(level<str[g] && j>f-2 && j>=2)  break;//����Ҫ�����ŵ�����������������
						if(level<str[g]) {
							//������
							word.insert(0, "( ");
							word.insert(word.length(), " )");
							j++;
						}
						level=str[g];
						 //���ѡ�����
						if(g==0) {	
							add(i,word, answer,g,sub,m);m++;
						}
						else if(g==1) {
						
							sub(i,word, answer,sub,m);m++;
							}
						else if(g==2) {
							String Boolean=div(i,j,f,word, answer,grade,sub,m,remb,k);
							if(Boolean.equals("j++")){
								j=j+2;
							
							}else if(Boolean.equals("false")) {
								i--;break;
							}
							m++;k++;
							
						}
						else {
							mul(i,word, answer,grade,sub,m);m++;
						}
						g = (int)(Math.random()*(str.length));
						if(answer[i]>=100) {
						g = (int)(Math.random()*(str.length-1));
						}
						while(g==g1 && j==0)
						{
						g = (int)(Math.random()*(str.length));
						if(answer[i]>=100) {
						g = (int)(Math.random()*(str.length-1));}
						}
					}		
				}
				word.insert(0, "("+(i+1)+")");
				if(grade==2 && g==3 && remb[k-1]!=0) {
				id.insert(id.length(), word+" = "+answer[i]+"..."+remb[k-1]+"\n");
				}
				else {
					id.insert(id.length(), word+" = "+answer[i]+"\n");
				}//����
				word.insert(word.length(),"\n");
				if(examine(sub,answer[i],list)) {
				p.write(word.toString());
				}else {
					i--;
				}	
			}

			p.write("\n");
			p.write(id.toString());
			p.close();
			out.close();
			
			} catch (IOException e) {
		} 
		
	}
	
	public static boolean examine(int strs[],int answer,Collection<String> list) {
		String str1=""+answer;
		Arrays.sort(strs);
		
		for(int str : strs) {
			str1=str1+str;
		}
		if(list.contains(str1)) {
			return false;
		}
		list.add(str1);
		return true;
	}
	
	public static void main(String[] args){
		// TODO �Զ����ɵķ������
		int[] str= new int[3];
		input(args,str);
		if(str[0]==0){
			return ;
		}
		n=str[1];
		grade=str[2];

		operation(n,grade);
	}

}