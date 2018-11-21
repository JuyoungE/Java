import java.util.Random;
import java.util.Scanner;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.io.*;

public class World{
	private static boolean skipDe = false; // �׽�Ʈ �뵵 : ������ ��ŵ ���� ����
	private static boolean skipTuto = false; // �׽�Ʈ �뵵 : Ʃ�丮�� ��ŵ ���� ����

	static int year = 2018;
	static int month = 5;
	static boolean evPreCheck = false; // ���� �̺�Ʈ ���� ����
	static int ending = 0; // ���� ���� - 1: �¸�, 0: �й�(Ÿ�ӿ���), -1: �й�(���)
	public static void main(String [] args){
		int LIMIT = 15; // �� 5���� ����������� �־��� ���� - (LIMIT-1)*12+8 ���� �־�����. (5������ ����)
	
		String str;
		Scanner s = new Scanner(System.in);
        
		Player player;
		Rival rival;
		
		if (skipTuto){
		player = new Player("TEST PLAYER");
		rival = new Rival("TEST RIVAL");
		}
		else{ // Ʃ�丮��
			System.out.println("??? : ȯ���մϴ�. ������!");
			de(1);
			System.out.println("������ : ���� �������� ù ����� ���� �������Դϴ�.");
			de(1);
			System.out.println("������ : �̾߱⸦ ������ ���� ���� ������� �̸��� �˷��ֽðھ��?");
			de(1);
			while(true){
				System.out.print("> �� ������� �̸�(10�� �̳�)��... ");
				de(1);
				str = s.nextLine();
				if (str.length() > 10){
				de(1);
				System.out.println("������ : ��������� �̸��� 10�ڸ� �ʰ��� �� �����ϴ�.");
				de(1);
				System.out.println("������ : �ٽ� �˷��ֽðھ��?");
				de(1);
				}
				else if (str.length() == 0){
					de(1);
					System.out.println("������ : ��������� �̸��� Ȯ������ ���߽��ϴ�.");
					de(1);
					System.out.println("������ : �ٽ� �˷��ֽðھ��?");
					de(1);
				}
				else break;
			}
		player = new Player(str); // �÷��̾� ��ü ����
		de(1);
		System.out.printf("������ : %s(��)��... ���� �̸��̳׿�!\n", str);
		de(1);
		System.out.println("������ : �׳����� ���̹��� �ִٰ� ����µ� �� ������� �̸��� �� �� �������?");
		de(1);
		while(true){
			System.out.print("> ���̹� ������� �̸�(10�� �̳�)��... ");
			de(1);
			str = s.nextLine();
			if (str.length() > 10){
				de(1);
				System.out.println("������ : ��������� �̸��� 10�ڸ� �ʰ��� �� �����ϴ�.");
				de(1);
				System.out.println("������ : �ٽ� �˷��ֽðھ��?");
				de(1);
			}
			else if (str.length() == 0){
				de(1);
				System.out.println("������ : ��������� �̸��� Ȯ������ ���߽��ϴ�.");
				de(1);
				System.out.println("������ : �ٽ� �˷��ֽðھ��?");
				de(1);
			}
			else break;
		}
		rival = new Rival(str); // ���̹� ��ü ����
		de(1);
		System.out.printf("������ : %s... ���� �̽����׿�.\n", str);
		de(1);
		System.out.println("������ : ...�����ϴ�!");
		de(1);
		System.out.println("������ : ������ �������� �ְ��� ��������� ��ǥ�� ��������� ��ϰ� �� ���Դϴ�.");
		de(1);
		System.out.println("������ : ���������� ������ �����ϱ� ���� �ڼ��� ������ �����ðھ��?");
		de(1);
		System.out.print("> �ڼ��� ������ ������?(Y/y �Է� �ø� Ʃ�丮�� ����) ");
		str = s.nextLine();
		if (str.equalsIgnoreCase("y")) tutorial();
		de(1);
		System.out.println("������ : �׷� ������ ���ϴ�!");
		de(1);
		System.out.println();
		de(1);
		System.out.println();
		de(1);
		System.out.println("--------------------------------------------- �ñ���  ������ ---------------------------------------------\n");
		de(3);
		}
      
		while(true){ // �ش� ���� �̺�Ʈ �� �ൿ ����
			System.out.printf("~ %d�� %d�� ~\n\n", year, month);
			de(1);
			if (year == 2018+LIMIT-1){
				System.out.println("�̹� �⵵�ȿ� 5���� ���� ��� ������ ���ϸ� ���ӿ��� �й��մϴ�.\n");
				de(1);
			}
			evPreCreate(player, rival); // ���� �̺�Ʈ ����
         
			player.decItemBooster();
			player.decItemInsurance();
         
			if (!evPreCheck){ // Ư�� �̺�Ʈ �̼��� �� �ൿ ���� 
			Merchant.sell(player);
			player.business();
			}
			else{
				System.out.println("> �̹� ���� �ϵ� �������� �׳� ���°� ���� �� ����.");
				de(1);
			}
         
			{ // ���� ���� ���� Ȯ��
			if (Restaurant.getStar() == 5){ // �� 5�� ȹ�� �� ���� �¸�
			ending = 1;
			break;
            }
            if (Restaurant.getPeriod() >= (LIMIT-1)*12+8){ // Ÿ�� ���� �� ���� �й�
               break;
            }
            if (player.getReput() <= 10){ // ���̹����� ���Ǻ� 10% ���Ϸ� �������� ������� ���� ���� �й�
               ending = -1;
               break;
            }
			}
         
			 System.out.printf("\n���°� : %s ���� �����̽��ϴ�!\n\n", month == 12 ? "���ص�" : "�̹� �޵�");
			 de(1);
			 System.out.printf("%s �Ѿ���� ����Ű�� �����ʽÿ�.\n", month == 12 ? "��������" : "���� �޷�");
			 s.nextLine();
			 evPreCheck = false; // ���� �̺�Ʈ ���࿩�� �ʱ�ȭ
			 goNextMonth(player); // ���� �޷� �Ѿ
			 de(1);
		}
		
		System.out.println();
		
		switch(ending){ // ���� ����
		case 1 : // ���� �¸�
			System.out.print("���°� : .");
			de(1);
			System.out.print(".");
			de(1);
			System.out.print(".");
			de(3);
			System.out.println("!");
			de(1);
			System.out.println("���°� : ���� 5���� ���� ��� �����̱���.");
			de(1);
			System.out.println("���°� : ��������� ã���ֽô� �մԵ��� ������ ���� ���� �� ��ź�ϰ� �ֽ��ϴ�.");
			de(1);
			System.out.println("���°� : ������ �� ���谡 �������� ��������� �ָ��ϰ���.");
			de(1);
			System.out.println("���°� : �����ε� ������ �� ���ô�!");
			de(1);
			System.out.println();
			de(1);
			System.out.println();
			de(1);
			System.out.println();
			de(1);
			System.out.println("�����մϴ�! ���ӿ��� �¸��ϼ̽��ϴ�!");
			break;
	
		case 0 : // Ÿ�� ����
			System.out.printf("���°� : �־��� %d���� �ð��� ��� �������ϴ�.\n", LIMIT);
			de(1);
			System.out.printf("���°� : �ּ��� ���� ��� %d���� ���� �����̱���.\n", Restaurant.getStar());
			de(1);
			System.out.println("���°� : ������ �����ߴ� �ɱ��?");
			de(1);
			System.out.println("���°� : �ƽ����� ������ ����� �̹� �ޱ����Դϴ�.");
			de(1);
			System.out.println("���°� : �������� ���� �ְ��� ��������� �� ������ �ָ��� �����ϰڽ��ϴ�.");
			de(1);
			System.out.println();
			de(1);
			System.out.println();
			de(1);
			System.out.println();
			de(1);
			System.out.println("���ӿ��� �й��ϼ̽��ϴ�. (Ÿ�� ����)");
			break;
			
		case -1: // ���
			System.out.println("> �̹� �޵� ��������� �ĸ��� ������.");
			de(1);
			System.out.printf("> �׿� ���� �ǳ��� %s ��������� �մ��� ���ĳ���.\n", rival.getName());
			de(1);
			System.out.println("> ��������ΰɱ�?");
			de(1);
			System.out.println("> �� ������ ���� ��������� ��Ѵٰ� ���Ĵ����...");
			de(1);
			System.out.println("> ��������� ���� �����̶� �ٸ� ���� ã�ƺ��� �ǰھ�.");
			de(1);
			System.out.println();
			de(1);
			System.out.println();
			de(1);
			System.out.println();
			de(1);
			System.out.println("���ӿ��� �й��Ͽ����ϴ�. (���)");
		}
		
		de(1);
		System.out.println("\n���ӿ����� ����� class ������ ���� ������ txt ���Ϸ� ����˴ϴ�.");
		de(1);
		
		saveLog(player);
		de(1);
		System.out.println("\n���ݱ��� ������ �÷��� ���ּż� �����մϴ�!");
	}
   
	static void de(double t){ // t�� ���� ������ �߻�
		if (skipDe) return;
      
		try{
			Thread.sleep((long)(750*t));
		}catch(Exception e){
			System.out.println(e); // ���� ������ ����Ѵ�.
		}
	}
   
	static void printLine(){
		System.out.println("----------------------------------------------------------------------------------------------------------");
	}
   
	static void tutorial(){ // Ʃ�丮��
		System.out.println();
		try{
			File f = new File("tutorial.txt");
         
			if (f.exists()){
				FileReader fread = new FileReader(f);
				int singleCh = 0;
				while((singleCh = fread.read()) != -1){
				System.out.print((char)singleCh);
				de(0.05);
				}
				fread.close();
				System.out.println();
			}
			else{
				System.out.println("tutorial.txt ������ �������� �ʽ��ϴ�.");
				de(1);
				System.out.println("Ʃ�丮���� �����մϴ�.");
				de(1);
			}
			
		}catch(FileNotFoundException e){
      
		}catch(IOException e){
			System.out.println(e);
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println();
	}
	
	static void saveLog(Player p){
		Calendar cal= Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("MMddHHmm");
		File f = new File(p.getName()+"_"+df.format(cal.getTime())+".txt");
		
		try{
			System.out.print("\n���� ��...");
			de(3);
			try(PrintWriter pw = new PrintWriter(f);){
				pw.format("������� � �Ⱓ: %d����" , p.getPeriod());
				pw.println();
				pw.format("���� �� ����: %d��" , p.getStar());
				pw.println();
				pw.format("���� �ڱ�: %d��" , p.getMoney());
				pw.println();
				pw.format("���� ����Ƽ: %d" , p.getQuality());
				pw.println();
				pw.format("���� ���� ����: %d" ,p.getReput());
				pw.println();
				pw.println();
				
				if(ending == 1){ 
					pw.format("���°��� ������ : ����մϴ�! �������� ������ ���� ������ �ֱ���!");
					pw.println();
					pw.format("�̽İ��� ������ : �� ����... ���� ���Ұ̴ϴ�.");
				}
				else if(ending == 0){
					pw.format("���°��� ������ : ���� �ּ��� ���ߴµ�... ���� �����ߴ��ɱ��?");
					pw.println();
					pw.format("�̽İ��� ������ : ����� ��������̾�����... ����� �̷��� �Ǿ� �ƽ����ϴ�.");
				}
				else{
					pw.format("���°��� ������ : �̰� �ƴѵ�... ��𼭺��� �߸��Ȱɱ��?");
					pw.println();
					pw.format("�̽İ��� ������ : ���￡�� �������� ���� �ָ� ���� ���ϴ� ��...");
				}
			}
			System.out.println(" ���� �Ϸ�!");			
		}catch(FileNotFoundException fnfe){
			fnfe.printStackTrace();
		}
	}
   
	static void evPreCreate(Player p, Rival r){ // ���� �̺�Ʈ ����
		double [] P = {0.1, 0.15, 0.2, 0.25, 0.3, 0}; // ���� ������ ���� ���� �߻� Ȯ��
      
		if (Math.random() <= P[Restaurant.getStar()]) evLoss(p, r);
      
		switch(month){
		case 4:
			evGourmet(p);
			break;
		case 8:
			evSeminar(p);
			break;
		case 12:
			evContest(p, r);
			break;
		}
	}
   
	static void evLoss(Player p, Rival r){ // ���� �̺�Ʈ : ���� �߻�
		int lossCase;
		int star;
      
		star = p.getStar();
      
		Random random = new Random();
		lossCase = random.nextInt(2);
      
		if(lossCase == 0) {
			p.wind();
		}
		else if(lossCase == 1) {
			r.rumor(p);
			p.rumor(r);
		}
      
		System.out.println();
	}
   
	static void evGourmet(Player p){ // ���� �̺�Ʈ(Ư��) : �̽İ�
     
		int std = Gourmet.getScorePraise(Restaurant.getStar());
        
		System.out.println("���°� : �̹� ���� �̽İ��� �湮�ϴ� ���Դϴ�.");
		de(1);
		System.out.println("- ���� ���� ������ �Ҹ� -");
		de(1);
		System.out.println("���°� : ��! �̽İ��� �����߳׿�.");
		de(1);
		System.out.println("�̽İ� : ���� ���� �ʰڽ��ϴ�. �غ��� �丮�� �ٷ� �� �� �� �������?");
		de(1);
		System.out.println("�̽İ����� �丮�� �����ߴ�.");
		de(1);
		System.out.print("�̽İ� �� ��...");
		de(3);
		System.out.println(" �� �Ϸ�!");
		de(1);
	  
		p.evaluated();
	 
		if(p.getScore() >= std){
			System.out.println("�̽İ� : �Ǹ��� ���̱���! � �� ���� ��Ӵϲ��� ���ֽ� �丮�� ���ö����ϴ�...");
			de(1);
			System.out.println("������ : �����ؿ�! ������ �����̳׿�.");
			de(1);
			p.addReput(5);
			System.out.println("\n���� ���ʽ�! ���� �濬��ȸ���� �ڵ�ĸ�� ����˴ϴ�."); 
			p.setPrize(true); // ������ ���� �� �濬��ȸ���� �ڵ�ĸ ����!
		}
		else if(p.getScore() >= std/2){
			System.out.println("�̽İ� : ������ ���Դϴ�. ���� �� ���� �� ���� ������ �����ֽñ�...");
			de(1);
			System.out.println("������ : �ƽ����� Ȥ���� �ƴ� ���� �����̳׿�.");
			p.addReput(2);
		}
		else {
			System.out.println("�̽İ� : ��������� ������ ���� ���� �ű��ϱ���.");
			de(1);
			System.out.println("������ : �����... Ȥ���̳׿�......");
			p.addReput(-20, false);
		}
		
		System.out.println();
		evPreCheck = true; // �̽İ� �̺�Ʈ ���� �� üũ 
	}
   
	static void evSeminar(Player p){ // ���� �̺�Ʈ(Ư��) : ���̳�
	    String str ;
		
		int [][] seminarQualityRange = {
			{20, 30},      
			{40, 60},
			{80, 120},
			{150, 200},
			{400, 500},
		};
        int [] seminarReput = {2,4,6,8,10};
        int [] cost = {20000, 150000, 750000, 5000000, 25000000, 0};
	        Scanner s = new Scanner(System.in);
			System.out.println("������ : �̹� ���� ���̳��� �ִ� ���Դϴ�.");
			de(1);
			System.out.println("������ : ��������� ����Ƽ�� ���� �� �ִ� ��ȣ�� ��������.");
			de(1);
			System.out.println("������ : ��������� "+cost[p.getStar()]+"�� �̳׿�. (���� ���� �ڱ� : "+p.getMoney()+"��)");
			de(1);
			
			if(cost[p.getStar()] > p.getMoney()){
				System.out.println("������ : ���� ���� �ڱ����δ� ���� ����� ���ڶ�׿�.");
				de(1);
				System.out.println("������ : �ƽ����� ��ȸ�� �����ϱ��. �̹� ���� �Ͽ� �����սô�!\n");	
			}			
			else{
				System.out.println("������ : ���̳��� �����Ͻðھ��?");
				de(1);
				System.out.print("> ���̳��� �����ұ�? (Y/y �Է� �ø� ���̳� ����) ");
				str = s.nextLine();
				if(str.equalsIgnoreCase("y")){
					p.addMoney(-cost[p.getStar()], false);
					de(1);
					System.out.print("\n���̳� ����...");
					de(3);
					System.out.println(" ��!");
					de(1);
					System.out.println("�ȸ��� ������ ���� ��ȸ����!");
					de(1);
					p.addQuality(seminarQualityRange[p.getStar()][0]+(int)(Math.random()*(seminarQualityRange[p.getStar()][1]-seminarQualityRange[p.getStar()][0]+1)));
					p.addReput(seminarReput[p.getStar()]);
				
					de(1);
					evPreCheck = true; // ���̳� �̺�Ʈ ���� �� üũ
				}
				else{ 
					System.out.println("���°� : �ƽ����� ��ȸ�� �����ϱ��. �̹� ���� �Ͽ� �����սô�!");
				}
			}
			System.out.println();
	}
   
	static void evContest(Player p, Rival r){ // ���� �̺�Ʈ(Ư��) : �濬 ��ȸ
			int [] cost = {40000, 300000, 1500000, 10000000, 50000000, 0};
			int stdScore = Gourmet.getScorePraise(p.getStar());
			
			Scanner s = new Scanner(System.in);
			String str ;
	        System.out.println("������ : �̹� �޿��� �濬 ��ȸ�� �ִ� ���Դϴ�.");
			de(1);
	        System.out.println("������ : �� ���� ��������� ����Ƽ�� �ܷ�� �߿��� ��ȸ����.");
			de(1);
	        System.out.println("������ : ��������� "+cost[p.getStar()]+"�� �̳׿�. (���� ���� �ڱ� : "+p.getMoney()+"��)");
			de(1);
			
			if(cost[p.getStar()] > p.getMoney()){
            System.out.println("������ : ���� ���� �ڱ����δ� ���� ����� ���ڶ�׿�.");
			de(1);
			System.out.println("������ : �ƽ����� ��ȸ�� �����ϱ��. �̹� ���� �Ͽ� �����սô�!\n");	
			}		
            else{			
				System.out.println("������ : �濬 ��ȸ�� �����Ͻðھ��?");
				de(1);
				System.out.print("> �濬 ��ȸ�� �����ұ�? (Y/y �Է� �ø� �濬 ��ȸ ����) ");
				str = s.nextLine();
				if(str.equalsIgnoreCase("y")){
					p.addMoney(-cost[p.getStar()], false);
					de(1);
					System.out.println("\n�濬 ��ȸ ����!");
					p.evaluated();
					de(1);
					
					System.out.print("���� �ɻ� ��... ");
					de(3);
					if (p.getScore() >= stdScore*0.5){
						System.out.println("8�� ����!");
						de(1);
						System.out.print("8�� �ɻ� ��... ");
						de(3);
						if (p.getScore() >= stdScore*0.75){
							System.out.println("4�� ����!");
							de(1);
							System.out.print("4�� �ɻ� ��... ");
							de(3);
							if (p.getScore() >= stdScore*1.5){
								System.out.println("��� ����!");
								de(1);
								System.out.printf("��! %s��(��) ��¿��� ������...\n", r.getName());
								de(1);
								System.out.println("���� ������?");
								de(1);
								System.out.print("�α��α�... ");
								de(5);
								r.evaluated();
								if (p.getScore() >= r.getScore()){
									System.out.println("���!\n");
									de(1);
									System.out.println("�濬��ȸ���� ����ߴ�!");
									p.addMoney(p.setRevenue()*10);
									de(1);
									p.addReput(10);
									de(1);
									if (p.getPrize()){
										if (p.getStar()+2 <= 5) System.out.print("\n���� �̽İ����� ������ ���� �ڵ�ĸ�� ����˴ϴ�.");
										de(1);
										p.addStar(2);
									}
									else{
										p.addStar(1);
									}
								}
								else{
									System.out.println("�ؿ��!\n");
									de(1);
									System.out.printf("���ϴ�! %s���� �й��ߴ�...\n", r.getName());
									de(1);
									p.addMoney(p.setRevenue()*5);
									de(1);
									p.addReput(7);
									if (p.getPrize()){
										de(1);
										System.out.println("���� �̽İ����� ������ ���� �ڵ�ĸ�� ����˴ϴ�.");
										p.addStar(1);
									}
								}
							}
							else{
								System.out.println("Ż��!\n");
								de(1);
								System.out.println("�ƽ��Ե� 4���� ���ƴ�...");
								de(1);
								p.addMoney(p.setRevenue()*2);
								de(1);
								p.addReput(5);
							}
						}
						else{
							System.out.println("Ż��!\n");
							de(1);
							System.out.println("�ƽ��Ե� 8���� ���ƴ�...");
							de(1);
							p.addMoney(p.setRevenue());
							de(1);
							p.addReput(3);
						}
					}
					else{
						System.out.println("Ż��!");
						de(1);
						System.out.println("�������� Ż���ߴ�...");
						de(1);
						p.addMoney(0);
						de(1);
						p.addReput(-20, false);
					}
					evPreCheck = true; // �濬 ��ȸ �̺�Ʈ ���� �� üũ
				}
				else{
					System.out.println("���°� : �ƽ����� ��ȸ�� �����ϱ��. �̹� ���� �Ͽ� �����սô�!\n");
				}
				System.out.println();
			}
	}
	static void goNextMonth(Player p){ // ���� �޷� �Ѿ
		p.close();
       
		if (month+1 > 12){
			year++;
			month = 1;
			p.setPrize(false); // 4���� ���� �ְ� ������ �ʱ�ȭ��Ų��!
		}
		else month++;
	}
}

class Restaurant{
	private String name; // ��������� �̸�
   
	private static int star; // ��������� �� ���� : �濬 ��ȸ ��� �� 1�� ȹ��, 5���� �Ǹ� Ŭ����, ���� ������ ���� ���� ���̵� ���

	private static int [] N = {1, 2, 5, 10, 20, 1000}; // �� ������ �ٸ� ���� ���� ���ʽ� (N��)

	private int score;
	// �̽İ� �� ���� : �̽İ� �̺�Ʈ���� �̽İ��� ����Ƽ�� ���� ��,
	// �濬 ��ȸ ���� : �÷��̾�� ����Ƽ�� ����, ���̹��� �� ������ ���� ������ �����ǰ� ��¿��� ���̹� ������ �Ѿ�� �濬 ��ȸ ���

	private static int period; // ���� ���� ��

	Restaurant(String name){ // ������� Ŭ������ ������
		  this.name = name;
		  period = 1;
	}
   
	// getter
	String getName(){
		return name;
	}
   
	static int getStar(){
		return star;
	}
   
	static int getN(int i){
		return N[i];
	}
   
	int getScore(){
		return score;
	}

    static int getPeriod(){   
		return period;
	}
   
	// setter �� adder
	void setScore(int score){
		this.score = score;
	}
   
	void addStar(int n){
		System.out.println();
		if (star+n < 5){
			System.out.printf("���� %d�� ȹ���Ͽ����ϴ�.\n", n);
			star += n;
		}
		else{
			System.out.printf("���� %d�� ȹ���Ͽ����ϴ�.\n", 5-star);
			star = 5;
		}
		System.out.printf("> ���� ���� �� : %d��\n", star);
	}
   
   
	void evaluated(){
                                                                 
	}
   
	void printUI(){
		System.out.printf("\n%s\n", name);
		System.out.printf("%4d�� %2d�� (��� �� %3d����)", World.year, World.month, period);
		for (int i = 0; i < 60; i++) System.out.print(" ");
		for (int i = 0; i < 5; i++) System.out.print(star > i ? "��" : "��");
		System.out.printf("[X%2d]\n", getN(getStar()));
	}
   
	static void close(){ // �� ���� ������ �� ����
		period++;
	}
}

final class Player extends Restaurant{
	private int money; // ��������� �ڱ�
	private int reput; // ��������� ���� ����
	private int quality; // ��������� ����Ƽ
	  
	private int itemBooster; // ���� �ν��� ������ ���� ����
	private int itemInsurance; // ���� ���� ������ ���� ����

	private boolean businessComplete = false;
	private boolean prize = false;

	Scanner s = new Scanner(System.in);
   
	Player(String name){ // �÷��̾� Ŭ������ ������
		super(name);
		money = 10000;
		reput = 50;
	}

	int setRevenue(){ // ���� ���� ����
		return (int)(getN(getStar())*(5000+100*quality)*Math.pow(5, (double)reput/50-1));
	}
   
	void setPrize(boolean prize){
		this.prize = prize;
	}
   
	// getter
	int getMoney(){
		return money;
	}
   
	int getReput(){
		return reput;
	}
   
	int getQuality(){
		return quality;
	} 

	boolean getPrize(){
		return prize;
	}
   
	// item
	int getItemBooster(){
		return itemBooster;
	}
   
	int getItemInsurance(){
		return itemInsurance;
	}
   
	void setItemBooster(int n){ // ����ν��� ������ ���� �� ��� ���� ����
		itemBooster = n;
		System.out.printf("����ν��Ͱ� %d���� �� ����˴ϴ�.\n", n);
	}
   
	void setItemInsurance(int n){ // ���غ��� ������ ���� �� ��� ���� ����
		itemInsurance = n;
		System.out.printf("���غ����� %d���� �� ����˴ϴ�.\n", n);
	}
   
	void decItemBooster(){ // ����ν��� ��� ���� ���� �� ���� ���� ���
		if (itemBooster <= 0) return;
		itemBooster--;
		if (itemBooster > 0) System.out.printf("����ν����� ��� ������ %d���� ���ҽ��ϴ�.\n", itemBooster);
		else System.out.println("����ν����� ��� ������ �������ϴ�.");
		World.de(1);
		if (itemInsurance == 0) System.out.println();
	}
   
	void decItemInsurance(){ // ���غ��� ��� ���� ���� �� ���� ���� ���
		if (itemInsurance <= 0) return;
		itemInsurance--;
		if (itemInsurance > 0) System.out.printf("���� ������ ��� ������ %d���� ���ҽ��ϴ�.\n", itemInsurance);
		else System.out.println("���� ������ ��� ������ �������ϴ�.");
		World.de(1);
		System.out.println();
	}
	
	// adder
	void addMoney(int n){
		System.out.println();
		if (n > 0){
			if (money + n < 2147483647){
				System.out.printf("�ڱ��� %d�� ȹ���Ͽ����ϴ�.\n", n);
				money += n;
			}
			else{ // �ڱ��� �Ǽ� ���� �ʰ��� ���� ������ �ʴ´�.
				if (money != 2147483647){
					System.out.printf("�ڱ��� %d�� ȹ���Ͽ����ϴ�.\n", 2147483647-money);
					money = 2147483647;
				}
				else System.out.println("�ڱ��� ������ �����ϴ�.");
			}
		}
		else if (n < 0){
			if (itemInsurance > 0){
				System.out.println("�������� ���غ��迡 ���ԵǾ� �־����ϴ�.");
				System.out.println("�ڱ��� �������� �ʾҽ��ϴ�!");
			}
			else{
				if (money + n > 0){
				System.out.printf("�ڱ��� %d�� �����Ͽ����ϴ�.\n", -n);
				money += n;
				}
				else{ // �ڱ��� 0 �̸��� ���� ������ �ʴ´�.
					if (money != 0){
					System.out.printf("�ڱ��� %d�� �����Ͽ����ϴ�.\n", money);
					money = 0;
					}
				else System.out.println("�ڱ��� �̹� �ּ�ġ�� �����Ͽ� �������� �ʽ��ϴ�."); // �ս� �߻� �̺�Ʈ�� ���Ͽ� �ڱ��� 0�� ��� ����
				}
			}
		}
		else System.out.println("�ڱ��� ������ �����ϴ�.");
      
		System.out.printf("> ���� ���� �ڱ� : %d��\n", money);
	}
	
	// Overload
	void addMoney(int n, boolean b){
		System.out.println();
		if (n > 0){
			if (money + n < 2147483647){
				System.out.printf("�ڱ��� %d�� ȹ���Ͽ����ϴ�.\n", n);
				money += n;
			}
			else{ // �ڱ��� �Ǽ� ���� �ʰ��� ���� ������ �ʴ´�.
				if (money != 2147483647){
					System.out.printf("�ڱ��� %d�� ȹ���Ͽ����ϴ�.\n", 2147483647-money);
					money = 2147483647;
				}
				else System.out.println("�ڱ��� ������ �����ϴ�.");
			}
		}
		else if (n < 0){
			if (money + n > 0){
			System.out.printf("�ڱ��� %d�� �����Ͽ����ϴ�.\n", -n);
			money += n;
			}
			else{ // �ڱ��� 0 �̸��� ���� ������ �ʴ´�.
				if (money != 0){
				System.out.printf("�ڱ��� %d�� �����Ͽ����ϴ�.\n", money);
				money = 0;
				}
				else System.out.println("�ڱ��� �̹� �ּ�ġ�� �����Ͽ� �������� �ʽ��ϴ�."); // �ս� �߻� �̺�Ʈ�� ���Ͽ� �ڱ��� 0�� ��� ����
			}
		}
		else System.out.println("�ڱ��� ������ �����ϴ�.");
		System.out.printf("> ���� ���� �ڱ� : %d��\n", money);
	}
		
	void addReput(int n){
		System.out.println();
		if (n > 0){
			if (reput + n < 100){
				System.out.printf("���� ������ %d �����Ͽ����ϴ�.\n", n);
				reput += n;
			}
			else{ // ���� ������ 100 �ʰ��� ���� ������ �ʴ´�.
				if (100-reput != 0){ 
					System.out.printf("���� ������ %d �����Ͽ����ϴ�.\n", 100-reput);
					reput = 100;
				}
				else System.out.println("���� ������ �̹� �ִ�ġ�� �����Ͽ� �������� �ʽ��ϴ�.");
			}
		}
		else if (n < 0){
			if (itemInsurance > 0){
				System.out.println("�������� ���غ��迡 ���ԵǾ� �־����ϴ�.");
				System.out.println("���� ������ �������� �ʾҽ��ϴ�!");
			}
			else{
				if (reput + n > 0){
					System.out.printf("���� ������ %d �����Ͽ����ϴ�.\n", -n);
					reput += n;
				}
				else{ // ���� ������ 0 �̸��� ���� ������ �ʴ´�.
					if (reput != 0){
						System.out.printf("���� ������ %d �����Ͽ����ϴ�.\n", reput);
						reput = 0;
					}
					else System.out.println("���� ������ �̹� �ּ�ġ�� �����Ͽ� �������� �ʽ��ϴ�.");
				}
			}
		}
		else System.out.println("���� ������ ������ �����ϴ�.");
      
		System.out.printf("> ���� ���� ���� : %d\n", reput);
	}
   
	// Overroad
	void addReput(int n, boolean b){
		System.out.println();
		if (n > 0){
			if (reput + n < 100){
				System.out.printf("���� ������ %d �����Ͽ����ϴ�.\n", n);
				reput += n;
			}
			else{ // ���� ������ 100 �ʰ��� ���� ������ �ʴ´�.
				if (100-reput != 0){ 
					System.out.printf("���� ������ %d �����Ͽ����ϴ�.\n", 100-reput);
					reput = 100;
				}
				else System.out.println("���� ������ �̹� �ִ�ġ�� �����Ͽ� �������� �ʽ��ϴ�.");
			}
		}
		else if (n < 0){
			if (reput + n > 0){
				System.out.printf("���� ������ %d �����Ͽ����ϴ�.\n", -n);
				reput += n;
			}
			else{ // ���� ������ 0 �̸��� ���� ������ �ʴ´�.
				if (reput != 0){
					System.out.printf("���� ������ %d �����Ͽ����ϴ�.\n", reput);
					reput = 0;
				}
				else System.out.println("���� ������ �̹� �ּ�ġ�� �����Ͽ� �������� �ʽ��ϴ�.");
			}
		}
		else System.out.println("���� ������ ������ �����ϴ�.");
      
		System.out.printf("> ���� ���� ���� : %d\n", reput);
	}
   
	void addQuality(int n){
		System.out.println();
		if (itemBooster > 0){
			n *= 2;
			System.out.println("����ν����� ȿ���� ȹ�� ����Ƽ�� 2�� ����˴ϴ�.");
		}
      
		if (quality + n < 2147483647){
			System.out.printf("����Ƽ�� %d �����Ͽ����ϴ�.\n", n);
			quality += n;
		}
		else{ // ����Ƽ�� �Ǽ� ���� �ʰ��� ���� ������ �ʴ´�.
			if (quality != 2147483647){
				System.out.printf("����Ƽ�� %d �����Ͽ����ϴ�.\n", 2147483647-quality);
				quality = 2147483647;
			}
			else System.out.println("����Ƽ�� �̹� �ִ�ġ�� �����Ͽ� �������� �ʽ��ϴ�.");
		}
      
		System.out.printf("> ���� ����Ƽ : %d\n", quality);
	}
   
	@Override
	void evaluated(){
		setScore(Gourmet.evaluatePlayer(quality));
	}

	@Override
	void printUI(){
		super.printUI();
		System.out.printf("�ڱ� : %10d��%24s����Ƽ : %10d%24s�������� : %3d[X%.1f]\n", money, "", quality, "", reput, Math.pow(5, (double)reput/50-1));
		World.printLine();
		System.out.println();
	}
   
	void business(){ // �� ���� ���� ���� �� ����   
		printUI();
		World.de(1);
		System.out.println("������ : �̹� �޿��� � ������ ���ðھ��?");
		World.de(1);
      
		while(!businessComplete){
			display();
			System.out.print("> ������ �ұ�? (�ش� ���� ��ȣ �Է�) ");
			switch(s.nextLine()){
			case "1":
				System.out.println();
				selling();
				break;
            
			case "2":
				System.out.println();
				actTraining();
				break;
            
			case "3":
				System.out.println();
				actCharity();
				break;
            
			default:
				System.out.println("\n������ : �� �� ���� ���Դϴ�.");
			}
			World.de(1);
			if(!businessComplete){
				System.out.println("������ : �ٽ� �ѹ� ����ֽðھ��?");
				World.de(1);
			}
		}
		businessComplete = false;
	}
   
	void display(){ // ���� ���� �׸� ����
		System.out.println();
		World.printLine();
		System.out.println("1. ���� ���� - �̹� �޿��� ������ �����մϴ�.");
		System.out.println("2. [��Ƽ��Ƽ] �丮 ���� - ���� �� ������ ������ �����ϴ�.");
		System.out.println("3. [��Ƽ��Ƽ] �ڼ� ��� - ����� �̿����� ã�ư� �Ļ縦 �����մϴ�.");
		World.printLine();
	}
   
	void selling(){ // ���� ����
		int stdQ = (int)(Gourmet.getScorePraise(getStar())*2.5);
		double stdP1, stdP2;
		double P = Math.random();
      
		if (quality < stdQ){
			stdP1 = (1-Math.pow((double)quality/stdQ, 2)); // ���� ��� ����
			stdP2 = 0.5*(1-Math.pow((double)quality/stdQ, 2)); // ���� �ʹ� ����
		}
		else{
			stdP1 = 0; // ���� ��� ����
			stdP2 = 0; // ���� �ʹ� ����
		}
      
		World.de(1);
		System.out.print("���� ��... ");
		World.de(3);
		System.out.println("���� ��!");
		World.de(1);
      
		if (P >= stdP1){ // ���� ���
			if (Math.random() > 0.5) System.out.println("�̹� �� ���� ��������� �մԵ��� �����߽��ϴ�.");
			else System.out.println("���� ���� �����Ͽ� ���� �ִ� �մԵ��� ���ҽ��ϴ�.");
			World.de(1);
			System.out.println("���� ���Ͱ� ���� ������ �����մϴ�!");
			World.de(1);
			addMoney((int)(2*setRevenue()));
			addReput(getStar()+1);
		}
		else if (P < stdP2){ // ���� �ʹ�
			if (Math.random() > 0.5) System.out.println("�̹� �޿��� ������� ���� �ĸ��� ���Ƚ��ϴ�.");
			else System.out.println("���� �մԵ��� ���÷����� ���� ���̾����ϴ�.");
			World.de(1);
			System.out.println("���� ���Ͱ� ���� ������ �����մϴ�...");
			World.de(1);
			addMoney((int)(0.5*setRevenue()));
			addReput(-(2*(getStar()+1)), false);
		}
		else{
			addMoney(setRevenue());
		}
		businessComplete = true;
	}
   
	void actTraining(){ // �丮 ����
		int [][] qualityRange = {
			{3, 5},      
			{5, 15},
			{15, 30},
			{30, 50},
			{50, 100},
			{1000, 10000}
		};
      
		World.de(1);
		System.out.print("�丮 ���� ��... ");
		World.de(3);
		System.out.println("�丮 ���� �Ϸ�!");
		World.de(1);
		System.out.println("�丮 �Ƿ��� ������ ����Դϴ�.");
		World.de(1);
		addQuality(qualityRange[getStar()][0]+(int)(Math.random()*(qualityRange[getStar()][1]-qualityRange[getStar()][0]+1)));
		World.de(1);
		businessComplete = true;
	}
   
	void actCharity(){ // �ڼ� ���
		int [][] reputRange = {
			{2, 4},
			{4, 6},
			{6, 8},
			{8, 10},
			{10, 15},
			{100, 100}
		};
      
		World.de(1);
		System.out.print("�ڼ� ��� ��... ");
		World.de(3);
		System.out.println("�ڼ� ��� �Ϸ�!");
		World.de(1);
		System.out.println("�ѵ��� �� ���̾����ϴ�.");
		World.de(1);
		addReput(reputRange[getStar()][0]+(int)(Math.random()*(reputRange[getStar()][1]-reputRange[getStar()][0]+1)));
		World.de(1);
		businessComplete = true;
	}
   
	void wind() {
		int star = Restaurant.getStar();
			System.out.println("��! ��� ����ģ ��ǳ ſ�� ��������� ������ ������������...");
			World.de(1);
			System.out.println("������ �����մϴ�.");
			World.de(1);
			addMoney(-(int)(money*(0.1+0.2*getStar())));
	}
   
	void rumor(Rival r) {
		int star = Restaurant.getStar(); 
	   
		System.out.println("��! "+ r.getName() +" ����������� ���� �ȵǴ� ��Ӹ� �۶߸��� �ִ�...");
		World.de(1);
		addReput(-5-(star+1)*5);
	}
}

final class Rival extends Restaurant{
	Rival(String name){
		super(name);
	}
   
	@Override
	public void evaluated(){
		setScore(Gourmet.evaluateRival(getStar()));
	}
   
	void rumor(Player p) {
		System.out.println("���̹� : "+ p.getName() + " ��������� ��ѱ� ��⸦ ����Ѵ��!");
		World.de(1);
	}
}
   
class Gourmet{ // �̽İ� �̺�Ʈ �� �濬 ��ȸ���� �ɻ� ���
	private static int [] scorePraise = {30, 150, 400, 1200, 3000, 0}; // ���� ������ ���� ���� ���� ����
   
	static int getScorePraise(int i){
		return scorePraise[i];
	}
   
	static int evaluatePlayer(int quality){ // �÷��̾� �� : ����Ƽ �״�� ������ �ݿ��ϴ� ������ ����
		return quality;
	}
   
	static int evaluateRival(int star){ // ���̹� �� : ���� ������ ���� ���� ���� ������ 1.9~2.1��� ����
		return (int)(scorePraise[star]*2.5); 
	}
}

class Merchant{
	private static int num = 0; // �Ǹ� Ƚ��
	private static int prise = 1000;
	private static String str; 
	private static boolean sellComplete = false;
   
	static void sell(Player p){ // ������ �Ǹ�
		prise = (int)(1000*Math.pow(2, num));
		sellComplete = false;
      
		Scanner s = new Scanner(System.in);
      
		if (p.getMoney() < prise) return; // �ڱ��� �����ϸ� ������ ���������� ����
      
		System.out.println("??? : �ȿ� ��ʴϱ�?");
		World.de(1);
		System.out.println("���°� : ������ ��������� ã�ƿԳ׿�.");
		World.de(1);
		System.out.println("���� : ���� ������� ��� ������ �ɸ��� ������ �Ĵ� �����Դϴ�.");
		World.de(1);
		System.out.println("���� : ���� ������ �غ������� õõ�� ���캸������.\n");
		World.de(1);
		while(!sellComplete){
			display(p);
			System.out.printf("> ���� ���� �ڱ� : %d��\n", p.getMoney());
			System.out.print("> ������ ���? (�ش� ������ ��ȣ �Է�, 0 �Է� �� ���� ����) ");
			switch(s.nextLine()){
			case "1":
				sellComplete = true;
				System.out.println("\n���� : �Ź� ����帳�ϴ�. ������ �� �˰ڽ��ϴ�!");
				World.de(1);
				p.addMoney(-prise, false); // ���غ��� ������
				num++;
				World.de(1);
				lottery(p);
				break;
            
			case "2":
				if (p.getItemBooster() == 0){
					sellComplete = true;
					System.out.println("\n���� : �Ź� ����帳�ϴ�. ������ �� �˰ڽ��ϴ�!");
					World.de(1);
					p.addMoney(-prise, false);
					System.out.println();
					p.setItemBooster(6);
					num++;
				}
				else{
					System.out.println("\n���� : �� �������� �̹� ���� ���Դϴ�.");
					World.de(1);
					System.out.printf("���� : %d���� �� ��� �Ⱓ�� ������ �ٽ� ������ �� �ֽ��ϴ�.\n", p.getItemBooster());
				}
				break;
            
			case "3":
				if (p.getItemInsurance() == 0){
					sellComplete = true;
					System.out.println("\n���� : �Ź� ����帳�ϴ�. ������ �� �˰ڽ��ϴ�!");
					World.de(1);
					p.addMoney(-prise, false);
					System.out.println();
					p.setItemInsurance(6);
					num++;
				}
				else{
					System.out.println("\n���� : �� �������� �̹� ���� ���Դϴ�.");
					World.de(1);
					System.out.printf("���� : %d���� �� ��� �Ⱓ�� ������ �ٽ� ������ �� �ֽ��ϴ�.\n", p.getItemInsurance());
				}
				break;
            
			case "0":
				sellComplete = true;
				System.out.println("\n���� : �̹� �޿��� ���� ������ �����ñ���.");
				World.de(1);
				System.out.println("���� : �ƽ����� ������ �� �˰ڽ��ϴ�!");
				break;
            
			default:
				System.out.println("\n���� : �� �����۵� ���ƺ��̳׿�.");
				World.de(1);
				System.out.println("���� : ������ ��ó �غ����� ���߽��ϴ�...");
			}
			World.de(1);
			if(!sellComplete){
				System.out.println("���� : �ٸ� ���� ��󺸽���!\n");
				World.de(1);
			}
		}
	}
   
	static void display(Player p){ // ������ ��� ����
		System.out.println("�� �ִ� �ϳ��� �����۸� ���� �����ϸ�, ���� �ø��� ��� ������ ������ 2��� �����մϴ�.");
		World.printLine();
		System.out.printf("1. ����[%d��] - 1���̸� �λ�����! �θ� �ƴϸ� ���� ������ �߰��� �����帳�ϴ�.\n", prise);
		System.out.printf("2. ����ν���[%s] - 6���� ���� ȹ���ϴ� ����Ƽ�� 2��! %s\n", p.getItemBooster() == 0 ? prise+"��" : p.getItemBooster()+"����", p.getItemBooster() == 0 ? "" : "(��� �� / ���� �Ұ�)");
		System.out.printf("3. ���غ���[%s] - 6���� ���� �߻��ϴ� ���� �̺�Ʈ�� ���� �ս� ��� ����! %s\n", p.getItemInsurance() == 0 ? prise+"��" : p.getItemInsurance()+"����", p.getItemInsurance() == 0 ? "" : "(��� �� / ���� �Ұ�)");
		World.printLine();
	}
   
	static void lottery(Player p){ // �Ｎ �ζ�
		double P = Math.random();
		System.out.println("\n������ �����?");
		World.de(1);
		System.out.print("�α��α�... ");
		World.de(3);
            
		if (P < 0.05){
			System.out.println("1��!");
			World.de(1);
			p.addMoney(p.setRevenue()*15+prise);
		}
		else if (P < 0.2){
			System.out.println("2��!");
			World.de(1);
			p.addMoney(p.setRevenue()*5+prise);
		}
		else if (P < 0.5){
			System.out.println("3��!");
			World.de(1);
			p.addMoney(p.setRevenue()*2+prise);
		}
		else System.out.println("��! ���� ��ȸ��..."); 
	}
}