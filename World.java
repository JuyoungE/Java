import java.util.Random;
import java.util.Scanner;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.io.*;

public class World{
	private static boolean skipDe = false; // 테스트 용도 : 딜레이 스킵 여부 결정
	private static boolean skipTuto = false; // 테스트 용도 : 튜토리얼 스킵 여부 결정

	static int year = 2018;
	static int month = 5;
	static boolean evPreCheck = false; // 사전 이벤트 수행 여부
	static int ending = 0; // 게임 엔딩 - 1: 승리, 0: 패배(타임오버), -1: 패배(폐업)
	public static void main(String [] args){
		int LIMIT = 15; // 별 5개를 모으기까지의 주어진 연도 - (LIMIT-1)*12+8 달이 주어진다. (5월부터 시작)
	
		String str;
		Scanner s = new Scanner(System.in);
        
		Player player;
		Rival rival;
		
		if (skipTuto){
		player = new Player("TEST PLAYER");
		rival = new Rival("TEST RIVAL");
		}
		else{ // 튜토리얼
			System.out.println("??? : 환영합니다. 셰프님!");
			de(1);
			System.out.println("조력자 : 저는 셰프님의 첫 출발을 도울 조력자입니다.");
			de(1);
			System.out.println("조력자 : 이야기를 나누기 전에 먼저 레스토랑 이름을 알려주시겠어요?");
			de(1);
			while(true){
				System.out.print("> 내 레스토랑 이름(10자 이내)은... ");
				de(1);
				str = s.nextLine();
				if (str.length() > 10){
				de(1);
				System.out.println("조력자 : 레스토랑의 이름은 10자를 초과할 수 없습니다.");
				de(1);
				System.out.println("조력자 : 다시 알려주시겠어요?");
				de(1);
				}
				else if (str.length() == 0){
					de(1);
					System.out.println("조력자 : 레스토랑의 이름을 확인하지 못했습니다.");
					de(1);
					System.out.println("조력자 : 다시 알려주시겠어요?");
					de(1);
				}
				else break;
			}
		player = new Player(str); // 플레이어 객체 선언
		de(1);
		System.out.printf("조력자 : %s(이)라... 멋진 이름이네요!\n", str);
		de(1);
		System.out.println("조력자 : 그나저나 라이벌이 있다고 들었는데 그 레스토랑 이름도 알 수 있을까요?");
		de(1);
		while(true){
			System.out.print("> 라이벌 레스토랑 이름(10자 이내)은... ");
			de(1);
			str = s.nextLine();
			if (str.length() > 10){
				de(1);
				System.out.println("조력자 : 레스토랑의 이름은 10자를 초과할 수 없습니다.");
				de(1);
				System.out.println("조력자 : 다시 알려주시겠어요?");
				de(1);
			}
			else if (str.length() == 0){
				de(1);
				System.out.println("조력자 : 레스토랑의 이름을 확인하지 못했습니다.");
				de(1);
				System.out.println("조력자 : 다시 알려주시겠어요?");
				de(1);
			}
			else break;
		}
		rival = new Rival(str); // 라이벌 객체 선언
		de(1);
		System.out.printf("조력자 : %s... 역시 촌스럽네요.\n", str);
		de(1);
		System.out.println("조력자 : ...좋습니다!");
		de(1);
		System.out.println("조력자 : 앞으로 셰프님은 최고의 레스토랑을 목표로 레스토랑을 운영하게 될 것입니다.");
		de(1);
		System.out.println("조력자 : 본격적으로 영업을 개시하기 전에 자세한 설명을 들으시겠어요?");
		de(1);
		System.out.print("> 자세한 설명을 들을까?(Y/y 입력 시만 튜토리얼 실행) ");
		str = s.nextLine();
		if (str.equalsIgnoreCase("y")) tutorial();
		de(1);
		System.out.println("조력자 : 그럼 건투를 빕니다!");
		de(1);
		System.out.println();
		de(1);
		System.out.println();
		de(1);
		System.out.println("--------------------------------------------- 궁극의  레시피 ---------------------------------------------\n");
		de(3);
		}
      
		while(true){ // 해당 달의 이벤트 및 행동 진행
			System.out.printf("~ %d년 %d월 ~\n\n", year, month);
			de(1);
			if (year == 2018+LIMIT-1){
				System.out.println("이번 년도안에 5개의 별을 모두 모으지 못하면 게임에서 패배합니다.\n");
				de(1);
			}
			evPreCreate(player, rival); // 사전 이벤트 수행
         
			player.decItemBooster();
			player.decItemInsurance();
         
			if (!evPreCheck){ // 특별 이벤트 미수행 시 행동 결정 
			Merchant.sell(player);
			player.business();
			}
			else{
				System.out.println("> 이번 달은 일도 많았으니 그냥 쉬는게 좋을 것 같다.");
				de(1);
			}
         
			{ // 게임 엔딩 여부 확인
			if (Restaurant.getStar() == 5){ // 별 5개 획득 시 게임 승리
			ending = 1;
			break;
            }
            if (Restaurant.getPeriod() >= (LIMIT-1)*12+8){ // 타임 오버 시 게임 패배
               break;
            }
            if (player.getReput() <= 10){ // 라이벌과의 평판비가 10% 이하로 떨어지면 폐업으로 인한 게임 패배
               ending = -1;
               break;
            }
			}
         
			 System.out.printf("\n조력가 : %s 수고 많으셨습니다!\n\n", month == 12 ? "올해도" : "이번 달도");
			 de(1);
			 System.out.printf("%s 넘어가려면 엔터키를 누르십시오.\n", month == 12 ? "내년으로" : "다음 달로");
			 s.nextLine();
			 evPreCheck = false; // 사전 이벤트 수행여부 초기화
			 goNextMonth(player); // 다음 달로 넘어감
			 de(1);
		}
		
		System.out.println();
		
		switch(ending){ // 게임 엔딩
		case 1 : // 게임 승리
			System.out.print("조력가 : .");
			de(1);
			System.out.print(".");
			de(1);
			System.out.print(".");
			de(3);
			System.out.println("!");
			de(1);
			System.out.println("조력가 : 드디어 5개의 별을 모두 모으셨군요.");
			de(1);
			System.out.println("조력가 : 레스토랑을 찾아주시는 손님들은 음식의 맛을 보고 늘 감탄하고 있습니다.");
			de(1);
			System.out.println("조력가 : 이제는 전 세계가 셰프님의 레스토랑을 주목하겠죠.");
			de(1);
			System.out.println("조력가 : 앞으로도 열심히 해 봅시다!");
			de(1);
			System.out.println();
			de(1);
			System.out.println();
			de(1);
			System.out.println();
			de(1);
			System.out.println("축하합니다! 게임에서 승리하셨습니다!");
			break;
	
		case 0 : // 타임 오버
			System.out.printf("조력가 : 주어진 %d년의 시간이 모두 지났습니다.\n", LIMIT);
			de(1);
			System.out.printf("조력가 : 최선을 다한 결과 %d개의 별을 모으셨군요.\n", Restaurant.getStar());
			de(1);
			System.out.println("조력가 : 무엇이 부족했던 걸까요?");
			de(1);
			System.out.println("조력가 : 아쉽지만 저와의 계약은 이번 달까지입니다.");
			de(1);
			System.out.println("조력가 : 언젠가는 세계 최고의 레스토랑이 될 때까지 멀리서 응원하겠습니다.");
			de(1);
			System.out.println();
			de(1);
			System.out.println();
			de(1);
			System.out.println();
			de(1);
			System.out.println("게임에서 패배하셨습니다. (타임 오버)");
			break;
			
		case -1: // 폐업
			System.out.println("> 이번 달도 레스토랑은 파리만 날린다.");
			de(1);
			System.out.printf("> 그에 비해 건너편 %s 레스토랑은 손님이 넘쳐난다.\n", rival.getName());
			de(1);
			System.out.println("> 여기까지인걸까?");
			de(1);
			System.out.println("> 내 주제에 무슨 레스토랑을 운영한다고 설쳐댔는지...");
			de(1);
			System.out.println("> 레스토랑을 접고 지금이라도 다른 길을 찾아봐야 되겠어.");
			de(1);
			System.out.println();
			de(1);
			System.out.println();
			de(1);
			System.out.println();
			de(1);
			System.out.println("게임에서 패배하였습니다. (폐업)");
		}
		
		de(1);
		System.out.println("\n게임에서의 기록은 class 파일이 속한 폴더에 txt 파일로 저장됩니다.");
		de(1);
		
		saveLog(player);
		de(1);
		System.out.println("\n지금까지 게임을 플레이 해주셔서 감사합니다!");
	}
   
	static void de(double t){ // t초 동안 딜레이 발생
		if (skipDe) return;
      
		try{
			Thread.sleep((long)(750*t));
		}catch(Exception e){
			System.out.println(e); // 예외 내용을 출력한다.
		}
	}
   
	static void printLine(){
		System.out.println("----------------------------------------------------------------------------------------------------------");
	}
   
	static void tutorial(){ // 튜토리얼
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
				System.out.println("tutorial.txt 파일이 존재하지 않습니다.");
				de(1);
				System.out.println("튜토리얼을 생략합니다.");
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
			System.out.print("\n저장 중...");
			de(3);
			try(PrintWriter pw = new PrintWriter(f);){
				pw.format("레스토랑 운영 기간: %d개월" , p.getPeriod());
				pw.println();
				pw.format("최종 별 개수: %d개" , p.getStar());
				pw.println();
				pw.format("최종 자금: %d원" , p.getMoney());
				pw.println();
				pw.format("최종 퀄리티: %d" , p.getQuality());
				pw.println();
				pw.format("최종 평판 점수: %d" ,p.getReput());
				pw.println();
				pw.println();
				
				if(ending == 1){ 
					pw.format("조력가의 한줄평 : 대단합니다! 셰프님을 열심히 도운 보람이 있군요!");
					pw.println();
					pw.format("미식가의 한줄평 : 그 맛을... 잊지 못할겁니다.");
				}
				else if(ending == 0){
					pw.format("조력가의 한줄평 : 정말 최선을 다했는데... 뭐가 부족했던걸까요?");
					pw.println();
					pw.format("미식가의 한줄평 : 노련한 레스토랑이었지만... 결과가 이렇게 되어 아쉽습니다.");
				}
				else{
					pw.format("조력가의 한줄평 : 이게 아닌데... 어디서부터 잘못된걸까요?");
					pw.println();
					pw.format("미식가의 한줄평 : 경쟁에서 뒤쳐지면 결코 주목 받지 못하는 법...");
				}
			}
			System.out.println(" 저장 완료!");			
		}catch(FileNotFoundException fnfe){
			fnfe.printStackTrace();
		}
	}
   
	static void evPreCreate(Player p, Rival r){ // 사전 이벤트 생성
		double [] P = {0.1, 0.15, 0.2, 0.25, 0.3, 0}; // 별의 개수에 따른 손해 발생 확률
      
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
   
	static void evLoss(Player p, Rival r){ // 사전 이벤트 : 손해 발생
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
   
	static void evGourmet(Player p){ // 사전 이벤트(특별) : 미식가
     
		int std = Gourmet.getScorePraise(Restaurant.getStar());
        
		System.out.println("조력가 : 이번 달은 미식가가 방문하는 달입니다.");
		de(1);
		System.out.println("- 가게 문이 열리는 소리 -");
		de(1);
		System.out.println("조력가 : 앗! 미식가가 도착했네요.");
		de(1);
		System.out.println("미식가 : 질질 끌지 않겠습니다. 준비한 요리를 바로 맛 볼 수 있을까요?");
		de(1);
		System.out.println("미식가에게 요리를 제공했다.");
		de(1);
		System.out.print("미식가 평가 중...");
		de(3);
		System.out.println(" 평가 완료!");
		de(1);
	  
		p.evaluated();
	 
		if(p.getScore() >= std){
			System.out.println("미식가 : 훌륭한 맛이군요! 어릴 적 저희 어머니께서 해주신 요리가 떠올랐습니다...");
			de(1);
			System.out.println("조력자 : 굉장해요! 극찬을 받으셨네요.");
			de(1);
			p.addReput(5);
			System.out.println("\n극찬 보너스! 올해 경연대회에서 핸디캡이 적용됩니다."); 
			p.setPrize(true); // 극찬을 받을 시 경연대회에서 핸디캡 적용!
		}
		else if(p.getScore() >= std/2){
			System.out.println("미식가 : 무난한 맛입니다. 다음 평가 때는 더 좋은 음식을 보여주시길...");
			de(1);
			System.out.println("조력자 : 아쉽지만 혹평이 아닌 것이 다행이네요.");
			p.addReput(2);
		}
		else {
			System.out.println("미식가 : 레스토랑이 망하지 않은 것이 신기하군요.");
			de(1);
			System.out.println("조력자 : 대단한... 혹평이네요......");
			p.addReput(-20, false);
		}
		
		System.out.println();
		evPreCheck = true; // 미식가 이벤트 수행 시 체크 
	}
   
	static void evSeminar(Player p){ // 사전 이벤트(특별) : 세미나
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
			System.out.println("조력자 : 이번 달은 세미나가 있는 달입니다.");
			de(1);
			System.out.println("조력자 : 레스토랑의 퀄리티를 쌓을 수 있는 절호의 찬스에요.");
			de(1);
			System.out.println("조력자 : 참가비용은 "+cost[p.getStar()]+"원 이네요. (현재 보유 자금 : "+p.getMoney()+"원)");
			de(1);
			
			if(cost[p.getStar()] > p.getMoney()){
				System.out.println("조력자 : 지금 가진 자금으로는 참가 비용이 모자라네요.");
				de(1);
				System.out.println("조력자 : 아쉽지만 기회는 많으니깐요. 이번 달의 일에 집중합시다!\n");	
			}			
			else{
				System.out.println("조력자 : 세미나에 참가하시겠어요?");
				de(1);
				System.out.print("> 세미나에 참가할까? (Y/y 입력 시만 세미나 참가) ");
				str = s.nextLine();
				if(str.equalsIgnoreCase("y")){
					p.addMoney(-cost[p.getStar()], false);
					de(1);
					System.out.print("\n세미나 참가...");
					de(3);
					System.out.println(" 끝!");
					de(1);
					System.out.println("안목을 넓히는 좋은 기회였다!");
					de(1);
					p.addQuality(seminarQualityRange[p.getStar()][0]+(int)(Math.random()*(seminarQualityRange[p.getStar()][1]-seminarQualityRange[p.getStar()][0]+1)));
					p.addReput(seminarReput[p.getStar()]);
				
					de(1);
					evPreCheck = true; // 세미나 이벤트 수행 시 체크
				}
				else{ 
					System.out.println("조력가 : 아쉽지만 기회는 많으니깐요. 이번 달의 일에 집중합시다!");
				}
			}
			System.out.println();
	}
   
	static void evContest(Player p, Rival r){ // 사전 이벤트(특별) : 경연 대회
			int [] cost = {40000, 300000, 1500000, 10000000, 50000000, 0};
			int stdScore = Gourmet.getScorePraise(p.getStar());
			
			Scanner s = new Scanner(System.in);
			String str ;
	        System.out.println("조력자 : 이번 달에는 경연 대회가 있는 날입니다.");
			de(1);
	        System.out.println("조력자 : 전 세계 레스토랑이 퀄리티를 겨루는 중요한 대회에요.");
			de(1);
	        System.out.println("조력자 : 참가비용은 "+cost[p.getStar()]+"원 이네요. (현재 보유 자금 : "+p.getMoney()+"원)");
			de(1);
			
			if(cost[p.getStar()] > p.getMoney()){
            System.out.println("조력자 : 지금 가진 자금으로는 참가 비용이 모자라네요.");
			de(1);
			System.out.println("조력자 : 아쉽지만 기회는 많으니깐요. 이번 달의 일에 집중합시다!\n");	
			}		
            else{			
				System.out.println("조력자 : 경연 대회에 참가하시겠어요?");
				de(1);
				System.out.print("> 경연 대회에 참가할까? (Y/y 입력 시만 경연 대회 참가) ");
				str = s.nextLine();
				if(str.equalsIgnoreCase("y")){
					p.addMoney(-cost[p.getStar()], false);
					de(1);
					System.out.println("\n경연 대회 시작!");
					p.evaluated();
					de(1);
					
					System.out.print("예선 심사 중... ");
					de(3);
					if (p.getScore() >= stdScore*0.5){
						System.out.println("8강 진출!");
						de(1);
						System.out.print("8강 심사 중... ");
						de(3);
						if (p.getScore() >= stdScore*0.75){
							System.out.println("4강 진출!");
							de(1);
							System.out.print("4강 심사 중... ");
							de(3);
							if (p.getScore() >= stdScore*1.5){
								System.out.println("결승 진출!");
								de(1);
								System.out.printf("앗! %s와(과) 결승에서 만났다...\n", r.getName());
								de(1);
								System.out.println("최종 성적은?");
								de(1);
								System.out.print("두구두구... ");
								de(5);
								r.evaluated();
								if (p.getScore() >= r.getScore()){
									System.out.println("우승!\n");
									de(1);
									System.out.println("경연대회에서 우승했다!");
									p.addMoney(p.setRevenue()*10);
									de(1);
									p.addReput(10);
									de(1);
									if (p.getPrize()){
										if (p.getStar()+2 <= 5) System.out.print("\n올해 미식가에게 극찬을 받은 핸디캡이 적용됩니다.");
										de(1);
										p.addStar(2);
									}
									else{
										p.addStar(1);
									}
								}
								else{
									System.out.println("준우승!\n");
									de(1);
									System.out.printf("분하다! %s에게 패배했다...\n", r.getName());
									de(1);
									p.addMoney(p.setRevenue()*5);
									de(1);
									p.addReput(7);
									if (p.getPrize()){
										de(1);
										System.out.println("올해 미식가에게 극찬을 받은 핸디캡이 적용됩니다.");
										p.addStar(1);
									}
								}
							}
							else{
								System.out.println("탈락!\n");
								de(1);
								System.out.println("아쉽게도 4강에 그쳤다...");
								de(1);
								p.addMoney(p.setRevenue()*2);
								de(1);
								p.addReput(5);
							}
						}
						else{
							System.out.println("탈락!\n");
							de(1);
							System.out.println("아쉽게도 8강에 그쳤다...");
							de(1);
							p.addMoney(p.setRevenue());
							de(1);
							p.addReput(3);
						}
					}
					else{
						System.out.println("탈락!");
						de(1);
						System.out.println("예선에서 탈락했다...");
						de(1);
						p.addMoney(0);
						de(1);
						p.addReput(-20, false);
					}
					evPreCheck = true; // 경연 대회 이벤트 수행 시 체크
				}
				else{
					System.out.println("조력가 : 아쉽지만 기회는 많으니깐요. 이번 달의 일에 집중합시다!\n");
				}
				System.out.println();
			}
	}
	static void goNextMonth(Player p){ // 다음 달로 넘어감
		p.close();
       
		if (month+1 > 12){
			year++;
			month = 1;
			p.setPrize(false); // 4월에 받은 최고 극찬을 초기화시킨다!
		}
		else month++;
	}
}

class Restaurant{
	private String name; // 레스토랑의 이름
   
	private static int star; // 레스토랑의 별 개수 : 경연 대회 우승 시 1개 획득, 5개가 되면 클리어, 별의 개수에 따라 게임 난이도 상승

	private static int [] N = {1, 2, 5, 10, 20, 1000}; // 별 개수에 다른 영업 수익 보너스 (N배)

	private int score;
	// 미식가 평가 점수 : 미식가 이벤트에서 미식가가 퀄리티에 따라 평가,
	// 경연 대회 점수 : 플레이어는 퀄리티에 따라, 라이벌은 별 개수에 따라 점수가 설정되고 결승에서 라이벌 점수를 넘어서면 경연 대회 우승

	private static int period; // 영업 개월 수

	Restaurant(String name){ // 레스토랑 클래스의 생성자
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
   
	// setter 및 adder
	void setScore(int score){
		this.score = score;
	}
   
	void addStar(int n){
		System.out.println();
		if (star+n < 5){
			System.out.printf("별을 %d개 획득하였습니다.\n", n);
			star += n;
		}
		else{
			System.out.printf("별을 %d개 획득하였습니다.\n", 5-star);
			star = 5;
		}
		System.out.printf("> 현재 보유 별 : %d개\n", star);
	}
   
   
	void evaluated(){
                                                                 
	}
   
	void printUI(){
		System.out.printf("\n%s\n", name);
		System.out.printf("%4d년 %2d월 (운영한 지 %3d개월)", World.year, World.month, period);
		for (int i = 0; i < 60; i++) System.out.print(" ");
		for (int i = 0; i < 5; i++) System.out.print(star > i ? "★" : "☆");
		System.out.printf("[X%2d]\n", getN(getStar()));
	}
   
	static void close(){ // 그 달이 종료할 시 수행
		period++;
	}
}

final class Player extends Restaurant{
	private int money; // 레스토랑의 자금
	private int reput; // 레스토랑의 평판 점수
	private int quality; // 레스토랑의 퀄리티
	  
	private int itemBooster; // 성장 부스터 아이템 남은 기한
	private int itemInsurance; // 손해 보험 아이템 남은 기한

	private boolean businessComplete = false;
	private boolean prize = false;

	Scanner s = new Scanner(System.in);
   
	Player(String name){ // 플레이어 클래스의 생성자
		super(name);
		money = 10000;
		reput = 50;
	}

	int setRevenue(){ // 영업 수익 결정
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
   
	void setItemBooster(int n){ // 성장부스터 아이템 구입 시 사용 기한 설정
		itemBooster = n;
		System.out.printf("성장부스터가 %d개월 간 적용됩니다.\n", n);
	}
   
	void setItemInsurance(int n){ // 손해보험 아이템 구입 시 사용 기한 설정
		itemInsurance = n;
		System.out.printf("손해보험이 %d개월 간 적용됩니다.\n", n);
	}
   
	void decItemBooster(){ // 성장부스터 사용 기한 감소 및 남은 기한 출력
		if (itemBooster <= 0) return;
		itemBooster--;
		if (itemBooster > 0) System.out.printf("성장부스터의 사용 기한이 %d개월 남았습니다.\n", itemBooster);
		else System.out.println("성장부스터의 사용 기한이 끝났습니다.");
		World.de(1);
		if (itemInsurance == 0) System.out.println();
	}
   
	void decItemInsurance(){ // 손해보험 사용 기한 감소 및 남은 기한 출력
		if (itemInsurance <= 0) return;
		itemInsurance--;
		if (itemInsurance > 0) System.out.printf("손해 보험의 계약 기한이 %d개월 남았습니다.\n", itemInsurance);
		else System.out.println("손해 보험의 계약 기한이 끝났습니다.");
		World.de(1);
		System.out.println();
	}
	
	// adder
	void addMoney(int n){
		System.out.println();
		if (n > 0){
			if (money + n < 2147483647){
				System.out.printf("자금을 %d원 획득하였습니다.\n", n);
				money += n;
			}
			else{ // 자금은 실수 범위 초과의 값을 가지지 않는다.
				if (money != 2147483647){
					System.out.printf("자금을 %d원 획득하였습니다.\n", 2147483647-money);
					money = 2147483647;
				}
				else System.out.println("자금의 변동이 없습니다.");
			}
		}
		else if (n < 0){
			if (itemInsurance > 0){
				System.out.println("다행히도 손해보험에 가입되어 있었습니다.");
				System.out.println("자금이 감소하지 않았습니다!");
			}
			else{
				if (money + n > 0){
				System.out.printf("자금을 %d원 지불하였습니다.\n", -n);
				money += n;
				}
				else{ // 자금은 0 미만의 값을 가지지 않는다.
					if (money != 0){
					System.out.printf("자금을 %d원 지불하였습니다.\n", money);
					money = 0;
					}
				else System.out.println("자금이 이미 최소치에 도달하여 감소하지 않습니다."); // 손실 발생 이벤트에 한하여 자금이 0인 경우 면제
				}
			}
		}
		else System.out.println("자금의 변동이 없습니다.");
      
		System.out.printf("> 현재 보유 자금 : %d원\n", money);
	}
	
	// Overload
	void addMoney(int n, boolean b){
		System.out.println();
		if (n > 0){
			if (money + n < 2147483647){
				System.out.printf("자금을 %d원 획득하였습니다.\n", n);
				money += n;
			}
			else{ // 자금은 실수 범위 초과의 값을 가지지 않는다.
				if (money != 2147483647){
					System.out.printf("자금을 %d원 획득하였습니다.\n", 2147483647-money);
					money = 2147483647;
				}
				else System.out.println("자금의 변동이 없습니다.");
			}
		}
		else if (n < 0){
			if (money + n > 0){
			System.out.printf("자금을 %d원 지불하였습니다.\n", -n);
			money += n;
			}
			else{ // 자금은 0 미만의 값을 가지지 않는다.
				if (money != 0){
				System.out.printf("자금을 %d원 지불하였습니다.\n", money);
				money = 0;
				}
				else System.out.println("자금이 이미 최소치에 도달하여 감소하지 않습니다."); // 손실 발생 이벤트에 한하여 자금이 0인 경우 면제
			}
		}
		else System.out.println("자금의 변동이 없습니다.");
		System.out.printf("> 현재 보유 자금 : %d원\n", money);
	}
		
	void addReput(int n){
		System.out.println();
		if (n > 0){
			if (reput + n < 100){
				System.out.printf("평판 점수가 %d 증가하였습니다.\n", n);
				reput += n;
			}
			else{ // 평판 점수는 100 초과의 값을 가지지 않는다.
				if (100-reput != 0){ 
					System.out.printf("평판 점수가 %d 증가하였습니다.\n", 100-reput);
					reput = 100;
				}
				else System.out.println("평판 점수가 이미 최대치에 도달하여 증가하지 않습니다.");
			}
		}
		else if (n < 0){
			if (itemInsurance > 0){
				System.out.println("다행히도 손해보험에 가입되어 있었습니다.");
				System.out.println("평판 점수가 감소하지 않았습니다!");
			}
			else{
				if (reput + n > 0){
					System.out.printf("평판 점수가 %d 감소하였습니다.\n", -n);
					reput += n;
				}
				else{ // 평판 점수는 0 미만의 값을 가지지 않는다.
					if (reput != 0){
						System.out.printf("평판 점수가 %d 감소하였습니다.\n", reput);
						reput = 0;
					}
					else System.out.println("평판 점수가 이미 최소치에 도달하여 감소하지 않습니다.");
				}
			}
		}
		else System.out.println("평판 점수의 변동이 없습니다.");
      
		System.out.printf("> 현재 평판 점수 : %d\n", reput);
	}
   
	// Overroad
	void addReput(int n, boolean b){
		System.out.println();
		if (n > 0){
			if (reput + n < 100){
				System.out.printf("평판 점수가 %d 증가하였습니다.\n", n);
				reput += n;
			}
			else{ // 평판 점수는 100 초과의 값을 가지지 않는다.
				if (100-reput != 0){ 
					System.out.printf("평판 점수가 %d 증가하였습니다.\n", 100-reput);
					reput = 100;
				}
				else System.out.println("평판 점수가 이미 최대치에 도달하여 증가하지 않습니다.");
			}
		}
		else if (n < 0){
			if (reput + n > 0){
				System.out.printf("평판 점수가 %d 감소하였습니다.\n", -n);
				reput += n;
			}
			else{ // 평판 점수는 0 미만의 값을 가지지 않는다.
				if (reput != 0){
					System.out.printf("평판 점수가 %d 감소하였습니다.\n", reput);
					reput = 0;
				}
				else System.out.println("평판 점수가 이미 최소치에 도달하여 감소하지 않습니다.");
			}
		}
		else System.out.println("평판 점수의 변동이 없습니다.");
      
		System.out.printf("> 현재 평판 점수 : %d\n", reput);
	}
   
	void addQuality(int n){
		System.out.println();
		if (itemBooster > 0){
			n *= 2;
			System.out.println("성장부스터의 효과로 획득 퀄리티가 2배 적용됩니다.");
		}
      
		if (quality + n < 2147483647){
			System.out.printf("퀄리티가 %d 증가하였습니다.\n", n);
			quality += n;
		}
		else{ // 퀄리티는 실수 범위 초과의 값을 가지지 않는다.
			if (quality != 2147483647){
				System.out.printf("퀄리티가 %d 증가하였습니다.\n", 2147483647-quality);
				quality = 2147483647;
			}
			else System.out.println("퀄리티가 이미 최대치에 도달하여 증가하지 않습니다.");
		}
      
		System.out.printf("> 현재 퀄리티 : %d\n", quality);
	}
   
	@Override
	void evaluated(){
		setScore(Gourmet.evaluatePlayer(quality));
	}

	@Override
	void printUI(){
		super.printUI();
		System.out.printf("자금 : %10d원%24s퀄리티 : %10d%24s평판점수 : %3d[X%.1f]\n", money, "", quality, "", reput, Math.pow(5, (double)reput/50-1));
		World.printLine();
		System.out.println();
	}
   
	void business(){ // 그 달의 업무 결정 및 수행   
		printUI();
		World.de(1);
		System.out.println("조력자 : 이번 달에는 어떤 업무를 보시겠어요?");
		World.de(1);
      
		while(!businessComplete){
			display();
			System.out.print("> 무엇을 할까? (해당 업무 번호 입력) ");
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
				System.out.println("\n조력자 : 할 수 없는 일입니다.");
			}
			World.de(1);
			if(!businessComplete){
				System.out.println("조력자 : 다시 한번 골라주시겠어요?");
				World.de(1);
			}
		}
		businessComplete = false;
	}
   
	void display(){ // 업무 선택 항목 나열
		System.out.println();
		World.printLine();
		System.out.println("1. 영업 매진 - 이번 달에는 영업에 매진합니다.");
		System.out.println("2. [액티비티] 요리 수련 - 깊은 산 속으로 수련을 떠납니다.");
		System.out.println("3. [액티비티] 자선 사업 - 어려운 이웃들을 찾아가 식사를 대접합니다.");
		World.printLine();
	}
   
	void selling(){ // 영업 매진
		int stdQ = (int)(Gourmet.getScorePraise(getStar())*2.5);
		double stdP1, stdP2;
		double P = Math.random();
      
		if (quality < stdQ){
			stdP1 = (1-Math.pow((double)quality/stdQ, 2)); // 영업 대박 기준
			stdP2 = 0.5*(1-Math.pow((double)quality/stdQ, 2)); // 영업 쪽박 기준
		}
		else{
			stdP1 = 0; // 영업 대박 기준
			stdP2 = 0; // 영업 쪽박 기준
		}
      
		World.de(1);
		System.out.print("영업 중... ");
		World.de(3);
		System.out.println("영업 끝!");
		World.de(1);
      
		if (P >= stdP1){ // 영업 대박
			if (Math.random() > 0.5) System.out.println("이번 달 내내 레스토랑에 손님들이 가득했습니다.");
			else System.out.println("음식 맛에 감동하여 팁을 주는 손님들이 많았습니다.");
			World.de(1);
			System.out.println("영업 수익과 평판 점수가 증가합니다!");
			World.de(1);
			addMoney((int)(2*setRevenue()));
			addReput(getStar()+1);
		}
		else if (P < stdP2){ // 영업 쪽박
			if (Math.random() > 0.5) System.out.println("이번 달에는 레스토랑 내에 파리만 날렸습니다.");
			else System.out.println("유독 손님들의 컴플레인이 많은 달이었습니다.");
			World.de(1);
			System.out.println("영업 수익과 평판 점수가 감소합니다...");
			World.de(1);
			addMoney((int)(0.5*setRevenue()));
			addReput(-(2*(getStar()+1)), false);
		}
		else{
			addMoney(setRevenue());
		}
		businessComplete = true;
	}
   
	void actTraining(){ // 요리 수련
		int [][] qualityRange = {
			{3, 5},      
			{5, 15},
			{15, 30},
			{30, 50},
			{50, 100},
			{1000, 10000}
		};
      
		World.de(1);
		System.out.print("요리 수련 중... ");
		World.de(3);
		System.out.println("요리 수련 완료!");
		World.de(1);
		System.out.println("요리 실력이 급증한 기분입니다.");
		World.de(1);
		addQuality(qualityRange[getStar()][0]+(int)(Math.random()*(qualityRange[getStar()][1]-qualityRange[getStar()][0]+1)));
		World.de(1);
		businessComplete = true;
	}
   
	void actCharity(){ // 자선 사업
		int [][] reputRange = {
			{2, 4},
			{4, 6},
			{6, 8},
			{8, 10},
			{10, 15},
			{100, 100}
		};
      
		World.de(1);
		System.out.print("자선 사업 중... ");
		World.de(3);
		System.out.println("자선 사업 완료!");
		World.de(1);
		System.out.println("뿌듯한 한 달이었습니다.");
		World.de(1);
		addReput(reputRange[getStar()][0]+(int)(Math.random()*(reputRange[getStar()][1]-reputRange[getStar()][0]+1)));
		World.de(1);
		businessComplete = true;
	}
   
	void wind() {
		int star = Restaurant.getStar();
			System.out.println("앗! 밤새 몰아친 강풍 탓에 레스토랑의 간판이 떨어져나갔다...");
			World.de(1);
			System.out.println("간판을 수리합니다.");
			World.de(1);
			addMoney(-(int)(money*(0.1+0.2*getStar())));
	}
   
	void rumor(Rival r) {
		int star = Restaurant.getStar(); 
	   
		System.out.println("앗! "+ r.getName() +" 레스토랑에서 말도 안되는 루머를 퍼뜨리고 있다...");
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
		System.out.println("라이벌 : "+ p.getName() + " 레스토랑은 비둘기 고기를 사용한대요!");
		World.de(1);
	}
}
   
class Gourmet{ // 미식가 이벤트 및 경연 대회에서 심사 담당
	private static int [] scorePraise = {30, 150, 400, 1200, 3000, 0}; // 별의 개수에 따른 극찬 기준 점수
   
	static int getScorePraise(int i){
		return scorePraise[i];
	}
   
	static int evaluatePlayer(int quality){ // 플레이어 평가 : 퀄리티 그대로 점수에 반영하는 것으로 설정
		return quality;
	}
   
	static int evaluateRival(int star){ // 라이벌 평가 : 별의 개수에 따른 극찬 기준 점수의 1.9~2.1배로 설정
		return (int)(scorePraise[star]*2.5); 
	}
}

class Merchant{
	private static int num = 0; // 판매 횟수
	private static int prise = 1000;
	private static String str; 
	private static boolean sellComplete = false;
   
	static void sell(Player p){ // 아이템 판매
		prise = (int)(1000*Math.pow(2, num));
		sellComplete = false;
      
		Scanner s = new Scanner(System.in);
      
		if (p.getMoney() < prise) return; // 자금이 부족하면 장사꾼이 떠나가도록 설정
      
		System.out.println("??? : 안에 계십니까?");
		World.de(1);
		System.out.println("조력가 : 누군가 레스토랑을 찾아왔네요.");
		World.de(1);
		System.out.println("장사꾼 : 저는 레스토랑 운영에 도움이 될만한 물건을 파는 장사꾼입니다.");
		World.de(1);
		System.out.println("장사꾼 : 좋은 물건을 준비했으니 천천히 살펴보시지요.\n");
		World.de(1);
		while(!sellComplete){
			display(p);
			System.out.printf("> 현재 보유 자금 : %d원\n", p.getMoney());
			System.out.print("> 무엇을 살까? (해당 아이템 번호 입력, 0 입력 시 구입 포기) ");
			switch(s.nextLine()){
			case "1":
				sellComplete = true;
				System.out.println("\n장사꾼 : 매번 감사드립니다. 다음에 또 뵙겠습니다!");
				World.de(1);
				p.addMoney(-prise, false); // 손해보험 미적용
				num++;
				World.de(1);
				lottery(p);
				break;
            
			case "2":
				if (p.getItemBooster() == 0){
					sellComplete = true;
					System.out.println("\n장사꾼 : 매번 감사드립니다. 다음에 또 뵙겠습니다!");
					World.de(1);
					p.addMoney(-prise, false);
					System.out.println();
					p.setItemBooster(6);
					num++;
				}
				else{
					System.out.println("\n장사꾼 : 그 아이템은 이미 적용 중입니다.");
					World.de(1);
					System.out.printf("장사꾼 : %d개월 후 사용 기간이 끝나면 다시 구매할 수 있습니다.\n", p.getItemBooster());
				}
				break;
            
			case "3":
				if (p.getItemInsurance() == 0){
					sellComplete = true;
					System.out.println("\n장사꾼 : 매번 감사드립니다. 다음에 또 뵙겠습니다!");
					World.de(1);
					p.addMoney(-prise, false);
					System.out.println();
					p.setItemInsurance(6);
					num++;
				}
				else{
					System.out.println("\n장사꾼 : 그 아이템은 이미 적용 중입니다.");
					World.de(1);
					System.out.printf("장사꾼 : %d개월 후 사용 기간이 끝나면 다시 구매할 수 있습니다.\n", p.getItemInsurance());
				}
				break;
            
			case "0":
				sellComplete = true;
				System.out.println("\n장사꾼 : 이번 달에는 구매 의향이 없으시군요.");
				World.de(1);
				System.out.println("장사꾼 : 아쉽지만 다음에 또 뵙겠습니다!");
				break;
            
			default:
				System.out.println("\n장사꾼 : 그 아이템도 좋아보이네요.");
				World.de(1);
				System.out.println("장사꾼 : 하지만 미처 준비하지 못했습니다...");
			}
			World.de(1);
			if(!sellComplete){
				System.out.println("장사꾼 : 다른 것을 골라보시죠!\n");
				World.de(1);
			}
		}
	}
   
	static void display(Player p){ // 아이템 목록 진열
		System.out.println("※ 최대 하나의 아이템만 구입 가능하며, 구입 시마다 모든 아이템 가격이 2배로 증가합니다.");
		World.printLine();
		System.out.printf("1. 복권[%d원] - 1등이면 인생역전! 꽝만 아니면 복권 가격은 추가로 돌려드립니다.\n", prise);
		System.out.printf("2. 성장부스터[%s] - 6개월 동안 획득하는 퀄리티가 2배! %s\n", p.getItemBooster() == 0 ? prise+"원" : p.getItemBooster()+"개월", p.getItemBooster() == 0 ? "" : "(사용 중 / 구입 불가)");
		System.out.printf("3. 손해보험[%s] - 6개월 동안 발생하는 손해 이벤트로 인한 손실 모두 면제! %s\n", p.getItemInsurance() == 0 ? prise+"원" : p.getItemInsurance()+"개월", p.getItemInsurance() == 0 ? "" : "(사용 중 / 구입 불가)");
		World.printLine();
	}
   
	static void lottery(Player p){ // 즉석 로또
		double P = Math.random();
		System.out.println("\n복권의 결과는?");
		World.de(1);
		System.out.print("두구두구... ");
		World.de(3);
            
		if (P < 0.05){
			System.out.println("1등!");
			World.de(1);
			p.addMoney(p.setRevenue()*15+prise);
		}
		else if (P < 0.2){
			System.out.println("2등!");
			World.de(1);
			p.addMoney(p.setRevenue()*5+prise);
		}
		else if (P < 0.5){
			System.out.println("3등!");
			World.de(1);
			p.addMoney(p.setRevenue()*2+prise);
		}
		else System.out.println("꽝! 다음 기회에..."); 
	}
}