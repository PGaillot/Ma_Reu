package service;

import com.gayo.maru.model.MeetModel;
import com.gayo.maru.model.PersonModel;

import java.sql.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public abstract class DummyMeetGenerator {

    public static List<PersonModel> PERSON_LIST = Arrays.asList(
            new PersonModel("pierre", "pr.gaillot@gmail.com"),
            new PersonModel("léa", "lea.coul@yahoo.fr"),
            new PersonModel("kevin", "kevin1000@hotmail.fr"),
            new PersonModel("angelique", "angelique@hotmail.com"),
            new PersonModel("tim", "darksasuke59@alice.fr"),
            new PersonModel("bénédicte", "bened@lycos.fr"),
            new PersonModel("clement", "satanmerde@gmx.com"),
            new PersonModel("mathias", "mathias@ctr.net"),
            new PersonModel("julien", "juliendubois@essteam.fr"),
            new PersonModel("claude", "claude@caramail.fr"),
            new PersonModel("clemence", "ccousin@essteam.fr"),
            new PersonModel("pierre", "pierrevanderstichel@essteam.fr"),
            new PersonModel("jean-sebastien", "Jean-Sébastien-blumenroeder@essteam.fr")
    );

    public static List<String> ROOMS_LIST = Arrays.asList(
            "bleu",
            "rouge",
            "jaune",
            "verte",
            "rose",
            "blanche",
            "noir"
    );

    public static List<MeetModel> DUMMY_MEETS = Arrays.asList(
            new MeetModel(PERSON_LIST.get(0).getName(), ROOMS_LIST.get(0), SetUpListMail(0), SetUpDate(), 2, "Adep -PTEM point Front" ),
            new MeetModel(PERSON_LIST.get(2).getName(), ROOMS_LIST.get(1), SetUpListMail(2), SetUpDate(), 2, "Creation logo FONGEP" ),
            new MeetModel(PERSON_LIST.get(3).getName(), ROOMS_LIST.get(2), SetUpListMail(3), SetUpDate(), 2, "Amenagement esspace 216" ),
            new MeetModel(PERSON_LIST.get(1).getName(), ROOMS_LIST.get(3), SetUpListMail(1), SetUpDate(), 2, "Presentation du projet Colabortatif BEECOM" ),
            new MeetModel(PERSON_LIST.get(2).getName(), ROOMS_LIST.get(4), SetUpListMail(2), SetUpDate(), 2, "Creation presonnage Nano" ),
            new MeetModel(PERSON_LIST.get(3).getName(), ROOMS_LIST.get(5), SetUpListMail(3), SetUpDate(), 2, "Aménagement interieur UNIK" ),
            new MeetModel(PERSON_LIST.get(4).getName(), ROOMS_LIST.get(6), SetUpListMail(4), SetUpDate(), 2, "La Méthodalogie chez ESS-TEAM" )
    );

    /**
     * Generate a List<MeetModel> of Meets
     * @return List<MeetModel>
     */
    static List<MeetModel> GenerateMeet() {return new ArrayList<>(DUMMY_MEETS);}

    /**
     * Generate a List<String> of Rooms
     * @return List<String>
     */
    static List<String> GenerateRooms() {return new ArrayList<>(ROOMS_LIST);}

    /**
     * Generate a MeetModel
     * @return MeetModel
     */
    public static MeetModel SetUpMeet() {
        Random randInt = new Random();
        int randLeader = randInt.nextInt(PERSON_LIST.size());
        int randRoom = randInt.nextInt(ROOMS_LIST.size());
        int randDuration = randInt.nextInt(8 - 2) + 2;
        MeetModel meet = new MeetModel(PERSON_LIST.get(randLeader).getName(), ROOMS_LIST.get(randRoom), SetUpListMail(randLeader), SetUpDate(), randDuration, "le topic");
        return meet;
    }

    /**
     * Generate a List<String> of mails
     * @param leader
     * @return String[]
     */
    private static String[] SetUpListMail(int leader) {
        Random randInt = new Random();
        List<PersonModel> mailList = new ArrayList<PersonModel>(PERSON_LIST);
        int minMeetGuest = 2;
        int listSize = randInt.nextInt(mailList.size() - minMeetGuest) + minMeetGuest;
        String[] mails = new String[listSize];
        // add the meet Leader at first position on list.
        mails[0] = (mailList.get(leader).getMail());
        mailList.remove(leader);
        // add random guest
        for (int i = 1; i < listSize; i++) {
            int randMail = randInt.nextInt(mailList.size());
            String mail = mailList.get(randMail).getMail();
            mails[i] = mail;
            mailList.remove(randMail);
        }
        return (String[]) mails;
    }

    /**
     * Generate a random date (after current day).
     * @return Date
     */
    private static Date SetUpDate() {
        Random random = new Random();
        Date currentDate;
        Calendar calendar = Calendar.getInstance();
        currentDate = new Date();
        calendar.setTime(currentDate);

        calendar.add(Calendar.MINUTE, random.nextInt(45));
        int currentMin = calendar.get(Calendar.MINUTE);
        if (currentMin > 0 && currentMin < 29){
            calendar.set(Calendar.MINUTE, 00);
        } else if(currentMin > 30 && currentMin < 59){
            calendar.set(Calendar.MINUTE, 30);
        }

        // Add days & hours to Calendar.
        calendar.add(Calendar.DATE, random.nextInt(3));
        calendar.add(Calendar.HOUR, random.nextInt(4));

        Date meetDate = calendar.getTime();

        return meetDate;
    }

}
