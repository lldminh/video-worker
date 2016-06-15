package com.golftec.teaching.common;

import com.golftec.teaching.model.*;
import com.golftec.teaching.model.data.SwingMeta;
import com.golftec.teaching.model.lesson.Coach;
import com.golftec.teaching.model.lesson.Student;
import com.golftec.teaching.model.types.EnvironmentType;
import com.golftec.teaching.model.types.MotionDataType;
import com.golftec.teaching.view.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.joda.time.DateTime;

import java.util.List;
import java.util.Map;

/**
 * NOTE: this is used to generate data for test/temporary purpose
 * Created by hoang on 5/23/15.
 */
@SuppressWarnings("SpellCheckingInspection")
public final class DataGenerator {

    @Deprecated
    public static List<EnvironmentView> randomEnvironmentsWithLessonList() {
        List<EnvironmentView> environments = Lists.newArrayList();
//        final List<LessonView> availableLessons = Many.randomLessonViews(EnvironmentType.GolftecToGo);
//        availableLessons.add(0, One.randomLessonView(EnvironmentType.GolftecToGo, false, 200, GTUtil.uuid(), Optional.of(true)));
//        environments.add(new EnvironmentView(String.valueOf(EnvironmentType.GolftecToGo.ordinal()),
//                                             EnvironmentType.GolftecToGo.displayName,
//                                             availableLessons,
//                                             Many.randomLessonViews(EnvironmentType.GolftecToGo, true)));
//        environments.add(new EnvironmentView(String.valueOf(EnvironmentType.MyProToGo.ordinal()),
//                                             EnvironmentType.MyProToGo.name(),
//                                             Many.randomLessonViews(EnvironmentType.MyProToGo),
//                                             Many.randomLessonViews(EnvironmentType.MyProToGo, true)));
//        environments.add(new EnvironmentView(String.valueOf(EnvironmentType.GolftecEvent.ordinal()),
//                                             EnvironmentType.GolftecEvent.name(),
//                                             Many.randomLessonViews(EnvironmentType.GolftecEvent),
//                                             Many.randomLessonViews(EnvironmentType.GolftecEvent, true)));
//        environments.add(new EnvironmentView(String.valueOf(EnvironmentType.GolftecOutdoors.ordinal()),
//                                             EnvironmentType.GolftecOutdoors.name(),
//                                             Many.randomLessonViews(EnvironmentType.GolftecOutdoors),
//                                             Many.randomLessonViews(EnvironmentType.GolftecOutdoors, true)));
        return environments;
    }

    /**
     * still need to makd atif and motion-data static
     */
    @Deprecated
    public static List<ProVideoView> staticProVideoList() {
        List<ProVideoView> toReturn = Lists.newArrayList();
        for (int i = 0; i < 20; i++) {
            toReturn.add(One.proVideoView                                                                      (i,
                                          i % 2 == 0,
                                          i % 2 != 0,
                                          i % 3 == 0,
                                          i % 3 != 0,
                                          i % 4 == 0,
                                          i % 4 != 0,
                                          One.randomAtifView(),
                                          Many.randomMotionDataMap(),
                                          new SwingMeta(DominantHand.Right,
                                                        Sex.Female,
                                                        100 + (i * 4),
                                                        SwingType.Chip,
                                                        Orientation.Front,
                                                        Order.Before))
                        );
        }
        return toReturn;
    }

    public static List<LessonView> staticLessonViewList() {
        List<LessonView> lessonViewList = Lists.newArrayList();
//        lessonViewList.add(One.lessonViewNoSwing("l-01-01", EnvironmentType.GolftecToGo));
//        lessonViewList.add(One.lessonViewNoSwing("l-01-02", EnvironmentType.GolftecToGo));
//        lessonViewList.add(One.lessonViewNoSwing("l-01-03", EnvironmentType.GolftecToGo));
//        lessonViewList.add(One.lessonViewNoSwing("l-01-04", EnvironmentType.GolftecToGo));
//        lessonViewList.add(One.lessonViewNoSwing("l-01-05", EnvironmentType.GolftecToGo));
//
//        lessonViewList.add(One.lessonViewNoSwing("l-02-01", EnvironmentType.GolftecToGo));
//        lessonViewList.add(One.lessonViewNoSwing("l-02-02", EnvironmentType.GolftecToGo));
//        lessonViewList.add(One.lessonViewNoSwing("l-02-03", EnvironmentType.GolftecToGo));
//        lessonViewList.add(One.lessonViewNoSwing("l-02-04", EnvironmentType.GolftecToGo));
//        lessonViewList.add(One.lessonViewNoSwing("l-02-05", EnvironmentType.GolftecToGo));
//
//        lessonViewList.add(One.lessonViewNoSwing("l-03-01", EnvironmentType.GolftecToGo));
//        lessonViewList.add(One.lessonViewNoSwing("l-03-02", EnvironmentType.GolftecToGo));
//        lessonViewList.add(One.lessonViewNoSwing("l-03-03", EnvironmentType.GolftecToGo));
//        lessonViewList.add(One.lessonViewNoSwing("l-03-04", EnvironmentType.GolftecToGo));
//        lessonViewList.add(One.lessonViewNoSwing("l-03-05", EnvironmentType.GolftecToGo));
//
//        lessonViewList.add(One.lessonViewNoSwing("l-04-01", EnvironmentType.GolftecToGo));
//        lessonViewList.add(One.lessonViewNoSwing("l-04-02", EnvironmentType.GolftecToGo));
//        lessonViewList.add(One.lessonViewNoSwing("l-04-03", EnvironmentType.GolftecToGo));
//        lessonViewList.add(One.lessonViewNoSwing("l-04-04", EnvironmentType.GolftecToGo));
//        lessonViewList.add(One.lessonViewNoSwing("l-04-05", EnvironmentType.GolftecToGo));

        // for get-schedule
//        final List<Student> students = staticStudentList();
//        lessonViewList.add(One.lessonViewNoSwing("l-11-01", EnvironmentType.UNKNOWN, LessonType.Short30.name, students.get(0)));
//        lessonViewList.add(One.lessonViewNoSwing("l-11-02", EnvironmentType.UNKNOWN, LessonType.Short60.name, students.get(1)));
//        lessonViewList.add(One.lessonViewNoSwing("l-11-03", EnvironmentType.UNKNOWN, LessonType.Playing.name, students.get(0)));
//        lessonViewList.add(One.lessonViewNoSwing("l-11-04", EnvironmentType.UNKNOWN, LessonType.Short30.name, students.get(2)));
//        lessonViewList.add(One.lessonViewNoSwing("l-11-05", EnvironmentType.UNKNOWN, LessonType.Short60.name, students.get(2)));
//        lessonViewList.add(One.lessonViewNoSwing("l-11-06", EnvironmentType.UNKNOWN, LessonType.Playing.name, students.get(0)));
//        lessonViewList.add(One.lessonViewNoSwing("l-11-07", EnvironmentType.UNKNOWN, LessonType.Short30.name, students.get(2)));
//        lessonViewList.add(One.lessonViewNoSwing("l-11-08", EnvironmentType.UNKNOWN, LessonType.Short60.name, students.get(2)));
//        lessonViewList.add(One.lessonViewNoSwing("l-11-09", EnvironmentType.UNKNOWN, LessonType.Playing.name, students.get(0)));
//        lessonViewList.add(One.lessonViewNoSwing("l-11-10", EnvironmentType.UNKNOWN, LessonType.Short30.name, students.get(2)));
//        lessonViewList.add(One.lessonViewNoSwing("l-11-11", EnvironmentType.UNKNOWN, LessonType.Short60.name, students.get(3)));

//        for (int i = 0; i < lessonViewList.size(); i++) {
//            LessonView lessonView = lessonViewList.get(i);
//            lessonView.modified = DateTime.now().minusDays(1).plusMinutes(i).toDate();
//        }
        return lessonViewList;
    }

    public static List<Coach> staticCoachList() {
        List<Store> stores = staticStoreList();
        List<Coach> list = Lists.newArrayList();
        list.add(One.coach("1000009991", "hoang", "1234", "Hoang Tran", "token-001", stores));
        list.add(One.coach("1000009992", "tao", "5678", "Tao Vo", "token-002", stores));
        list.add(One.coach("1000009993", "son", "9012", "Son Le Duc", "token-003", stores));
        return list;
    }

    public static List<Store> staticStoreList() {
        List<Store> stores = Lists.newArrayList();
        {
            Store store = new Store();
            store.id = "1000000010";
            store.name = "Headquarters";
            stores.add(store);
        }
        {
            Store store = new Store();
            store.id = "1000000001";
            store.name = "Denver Tech Center";
            stores.add(store);
        }
        return stores;
    }

    @Deprecated
    public static List<Student> staticStudentList() {
        List<Student> list = Lists.newArrayList();
//        list.add(new Student("stud-001", "Loc", "Doan", "loc001@gmail.com"));
//        list.add(new Student("stud-002", "Tram", "Kieu", "tram002@gmail.com"));
//        list.add(new Student("stud-003", "Anh", "Hue", "hueanh003@gmail.com"));
//        list.add(new Student("stud-004", "Al", "Wells", "alwells004@gmail.com"));
        list.add(new Student("_19990001_002-00-5264", "19990001", "002-00-5264", "Andy", "Hilts", "andy_hilts@golftec.com", "5264-166-987"));
        list.add(new Student("_19990002_001-02-0363", "19990002", "001-02-0363", "Geoff", "Hiland", "geoff_hiland@golftec.com", "0363-166-987"));
        list.add(new Student("_19990003_002-04-1411", "19990003", "002-04-1411", "Jeremy", "Beck", "jeremy_beck@golftec.com", "1411-166-987"));
        list.add(new Student("_19990004_003-03-1347", "19990004", "003-03-1347", "Nick", "Clearwater", "nick_clearwater@golftec.com", "1347-166-987"));
        list.add(new Student("_19990005_001-02-0582", "19990005", "001-02-0582", "Patrick", "Nuber", "patrick_nuber@golftec.com", "0582-166-987"));
        list.add(new Student("_19990006_999-11-0215", "19990006", "999-11-0215", "Robert", "Klados", "robert_klados@golftec.com", "0215-166-987"));
        list.add(new Student("_19990007_049-16-5667", "19990007", "049-16-5667", "Ryan", "Spracklen", "ryan_spracklen@golftec.com", "5667-166-987"));
//        list.add(new Student("stud-010", "Flynn", "McClain", "flynn_mcclain@golftec.com"));
        return list;
    }

    @Deprecated
    public static List<ScheduleItemViewsByDay> staticScheduleList() {
        List<Coach> coaches = staticCoachList();
        List<LessonView> lessons = staticLessonViewList();

        List<ScheduleItemViewsByDay> toReturn = Lists.newArrayList
                (
                        new ScheduleItemViewsByDay("Fri Jul 3, 2015",
                                                   Lists.newArrayList
                                                           (
                                                                   new ScheduleItemView(GTUtil.uuid(), new DateTime(2015, 7, 3, 10, 30), coaches.get(0), lessons.get(0)),
                                                                   new ScheduleItemView(GTUtil.uuid(), new DateTime(2015, 7, 3, 14, 30), coaches.get(0), lessons.get(1))
                                                           )
                        ),
                        new ScheduleItemViewsByDay("Sat Jul 4, 2015",
                                                   Lists.newArrayList
                                                           (
                                                                   new ScheduleItemView(GTUtil.uuid(), new DateTime(2015, 7, 4, 9, 20), coaches.get(1), lessons.get(2)),
                                                                   new ScheduleItemView(GTUtil.uuid(), new DateTime(2015, 7, 4, 11, 20), coaches.get(2), lessons.get(3)),
                                                                   new ScheduleItemView(GTUtil.uuid(), new DateTime(2015, 7, 4, 15, 10), coaches.get(1), lessons.get(4))
                                                           )
                        ),
                        new ScheduleItemViewsByDay("Mon Jul 6, 2015",
                                                   Lists.newArrayList
                                                           (
                                                                   new ScheduleItemView(GTUtil.uuid(), new DateTime(2015, 7, 6, 9, 20), coaches.get(1), lessons.get(5)),
                                                                   new ScheduleItemView(GTUtil.uuid(), new DateTime(2015, 7, 6, 11, 20), coaches.get(2), lessons.get(6)),
                                                                   new ScheduleItemView(GTUtil.uuid(), new DateTime(2015, 7, 6, 15, 10), coaches.get(1), lessons.get(7))
                                                           )
                        ),
                        new ScheduleItemViewsByDay("Tue Jul 7, 2015",
                                                   Lists.newArrayList
                                                           (
                                                                   new ScheduleItemView(GTUtil.uuid(), new DateTime(2015, 7, 7, 9, 20), coaches.get(1), lessons.get(8)),
                                                                   new ScheduleItemView(GTUtil.uuid(), new DateTime(2015, 7, 7, 11, 20), coaches.get(2), lessons.get(9)),
                                                                   new ScheduleItemView(GTUtil.uuid(), new DateTime(2015, 7, 7, 15, 10), coaches.get(1), lessons.get(10))
                                                           )
                        )
                );

        return toReturn;
    }

    private static final class Common {

        private static List<String> videoUris;
        private static List<String> thumbnailUris;
        private static List<String> peopleNames;

        static {
            videoUris = Lists.newArrayList();
            videoUris.add("http://45.33.66.59:40003/stream/test-videos/video01.mp4");
            videoUris.add("http://45.33.66.59:40003/stream/test-videos/video02.mp4");
            videoUris.add("http://45.33.66.59:40003/stream/test-videos/video03.mp4");
            videoUris.add("http://45.33.66.59:40003/stream/test-videos/video04.mp4");
            videoUris.add("http://45.33.66.59:40003/stream/test-videos/video05.mp4");
            videoUris.add("http://45.33.66.59:40003/stream/test-videos/video06.mp4");
            videoUris.add("http://45.33.66.59:40003/stream/test-videos/video07.mp4");
            videoUris.add("http://45.33.66.59:40003/stream/test-videos/video08.mp4");
            videoUris.add("http://45.33.66.59:40003/stream/test-videos/video09.mp4");
            videoUris.add("http://45.33.66.59:40003/stream/test-videos/video10.mp4");
            videoUris.add("http://45.33.66.59:40003/stream/test-videos/video11.mp4");
            videoUris.add("http://45.33.66.59:40003/stream/test-videos/video12.mp4");
            videoUris.add("http://45.33.66.59:40003/stream/test-videos/video13.mp4");
            videoUris.add("http://45.33.66.59:40003/stream/test-videos/video14.mp4");
            videoUris.add("http://45.33.66.59:40003/stream/test-videos/video15.mp4");
            videoUris.add("http://45.33.66.59:40003/stream/test-videos/video16.mp4");
            videoUris.add("http://45.33.66.59:40003/stream/test-videos/video17.mp4");
            videoUris.add("http://45.33.66.59:40003/stream/test-videos/video18.mp4");
            videoUris.add("http://45.33.66.59:40003/stream/test-videos/video19.mp4");
            videoUris.add("http://45.33.66.59:40003/stream/test-videos/video20.mp4");
            videoUris.add("http://45.33.66.59:40003/stream/test-videos/video21.mp4");
            videoUris.add("http://45.33.66.59:40003/stream/test-videos/video22.mp4");
            videoUris.add("http://45.33.66.59:40003/stream/test-videos/video23.mp4");
            videoUris.add("http://45.33.66.59:40003/stream/test-videos/video24.mp4");
            videoUris.add("http://45.33.66.59:40003/stream/test-videos/video25.mp4");
            videoUris.add("http://45.33.66.59:40003/stream/test-videos/video26.mp4");
            videoUris.add("http://45.33.66.59:40003/stream/test-videos/video27.mp4");
            videoUris.add("http://45.33.66.59:40003/stream/test-videos/video28.mp4");
            videoUris.add("http://45.33.66.59:40003/stream/test-videos/video29.mp4");
            videoUris.add("http://45.33.66.59:40003/stream/test-videos/video30.mp4");
            videoUris.add("http://45.33.66.59:40003/stream/test-videos/video31.mp4");
            videoUris.add("http://45.33.66.59:40003/stream/test-videos/video32.mp4");
            videoUris.add("http://techslides.com/demos/sample-videos/small.mp4");

            thumbnailUris = Lists.newArrayList();
            thumbnailUris.add("http://www.mollymookgolf.com.au/wp-content/uploads/Golf-page-images-hilltop3.jpg");
            thumbnailUris.add("http://goo.gl/hiQgSl");
            thumbnailUris.add("http://goo.gl/oqOAxH");
            thumbnailUris.add("http://goo.gl/mqNJp3");
            thumbnailUris.add("http://www.mollymookgolf.com.au/wp-content/uploads/Golf-page-images-hilltop2.jpg");
            thumbnailUris.add("http://goo.gl/D2Hzf6");
            thumbnailUris.add("http://goo.gl/iGlf8v");
            thumbnailUris.add("https://goo.gl/P5OPLI");
            thumbnailUris.add("http://goo.gl/3AfjvD");
            thumbnailUris.add("http://goo.gl/QO9yxo");
            thumbnailUris.add("http://goo.gl/hdXJLT");
            thumbnailUris.add("http://goo.gl/m1GkQq");
            thumbnailUris.add("http://goo.gl/ly5dWQ");
            thumbnailUris.add("http://goo.gl/OLO7Ka");
            thumbnailUris.add("http://static1.squarespace.com/static/53816326e4b09192d2a666ec/t/5390a027e4b0eef6727a2ed7/1401987111854/free-golf-pacakage.jpg?format=1500w");
            thumbnailUris.add("http://www.golfderio.com/img/full-slider/guante-blanco-sol-derio-golf.jpg");
            thumbnailUris.add("http://i.investopedia.com/inv/articles/slideshow/expensive-rounds-of-golf/golf_intro.jpg");
            thumbnailUris.add("http://www.bcgolfmuseum.org/file/2014/10/charityclassic-golf-960x410.jpg");
            thumbnailUris.add("http://johnloganfoundation.com.au/wp-content/uploads/2015/03/golf-green.jpg");
            thumbnailUris.add("http://chandlerparkgolfcourse.com/images/golf%20course.jpg");
            thumbnailUris.add("http://www.tahoesbest.com/sites/default/files/northstar-golf-1.jpg?1345490193");
            thumbnailUris.add("http://www.golfdigest.com/images/magazine/2014-07/maar02-music-on-golf-course.jpg");
            thumbnailUris.add("http://www.bw-chilworthmanor.co.uk/pictures/0/Golf1_608x355.jpg");
            thumbnailUris.add("https://thegratefulgolfer.files.wordpress.com/2012/11/678109_golf-tips-long-chip-jpg.jpg");
            thumbnailUris.add("http://content5.videojug.com/9a/9a03bbf9-7307-54ff-6bee-ff0008c90d2c/how-to-improve-your-chipping-technique.WidePlayer.jpg?v3");
            thumbnailUris.add("http://content5.videojug.com/c0/c067e644-5ec6-56ed-0d45-ff0008c94c9b/golf-downhill-chipping.WidePlayer.jpg");
            thumbnailUris.add("http://perfectpitchgolf.com/wp-content/uploads/2013/06/chipping-featured-image.jpg");
            thumbnailUris.add("http://www.examiner.com/images/blog/wysiwyg/image/chip_shot(7).jpg");
            thumbnailUris.add("http://www.hittingthegreen.com/wp-content/uploads/2011/11/chipping_close_up.jpg");
            thumbnailUris.add("http://www.giocodelgolf.it/wp-content/uploads/2013/04/chip_golf.jpg");
            thumbnailUris.add("http://golftips.golfsmith.com/DM-Resize/photos.demandstudios.com/getty/article/41/80/78494159.jpg?w=600&h=600&keep_ratio=1");
            thumbnailUris.add("http://www.golfdigest.com/images/instruction/2013-01/inar01_sean_foley_chipping.jpg");

            peopleNames = Lists.newArrayList();
            peopleNames.add("Hoang");
            peopleNames.add("Loc");
            peopleNames.add("Tao");
            peopleNames.add("Son");
            peopleNames.add("Tram");
            peopleNames.add("Anh");
            peopleNames.add("Hue");
            peopleNames.add("Alvin");
            peopleNames.add("Tran");
            peopleNames.add("Dinh");
            peopleNames.add("Le");
            peopleNames.add("Vo");
            peopleNames.add("Wells");
            peopleNames.add("Peter");
            peopleNames.add("John");
        }
    }

    private static final class Many {

        @Deprecated
        static Map<Integer, List<MotionDataView2>> randomMotionDataMap() {
            final Map<Integer, List<MotionDataView2>> toReturn = Maps.newConcurrentMap();
            // can step from 1 to 20 srcFrame
            for (int frame = GTUtil.positiveRandom(50); frame < 200; frame += GTUtil.randomInRange(1, 30)) {
                toReturn.put(frame, randomMotionDataView2List(frame));
            }
            return toReturn;
        }

        @Deprecated
        static List<MotionDataView2> randomMotionDataView2List(int frame) {
            final List<MotionDataView2> toReturn = Lists.newArrayList();
            // if there's motion data for a srcFrame, then it should have at least 1 item
            for (int i = 0; i < GTUtil.randomInRange(1, 2); i++) {
                MotionDataView2 mv = new MotionDataView2();
                mv.frame = frame;
                mv.value = GTUtil.positiveRandom(200);
                mv.type = GTUtil.randomItemFromList(MotionDataType.values());
                mv.hexColor = String.format("%02x%02x%02x", GTUtil.positiveRandom(256), GTUtil.positiveRandom(256), GTUtil.positiveRandom(256));
                toReturn.add(mv);
            }
            return toReturn;
        }
    }

    private static final class One {

        static LessonView lessonViewNoSwing(String lessonId, EnvironmentType environmentType, String lessonType, Student student) {
            return new LessonView(lessonId, lessonType, environmentType,
                                  student,
                                  Lists.newArrayList(),
                                  String.format("script for %s %s", environmentType.displayName, lessonId)
            );
        }

        static ProVideoView proVideoView(int i, boolean isLeft, boolean isRight, boolean isFront, boolean isSide, boolean isPreferred, boolean isHidden, AtifView atifView, Map<Integer, List<MotionDataView2>> motionDataMap, SwingMeta swingMeta) {
            ProVideoView proVideoView = new ProVideoView();
            proVideoView.id = String.format("proVid_%d", i);
            proVideoView.thumbnailUri = Common.thumbnailUris.get(i);
            proVideoView.isLeft = isLeft;
            proVideoView.isRight = isRight;
            proVideoView.isFront = isFront;
            proVideoView.isSide = isSide;
            //TODO: preferred and hidden is based on user/coach
            proVideoView.isPreferred = isPreferred;
            proVideoView.isHidden = isHidden;
            proVideoView.atif = atifView;
            proVideoView.motionDataMap = motionDataMap;
            proVideoView.proName = String.format("pn %d", i);
//            proVideoView.videoUri = Common.videoUris.get(i);
            proVideoView.videoUri = Common.videoUris.get(0);
            proVideoView.swingMetadata = swingMeta;
            return proVideoView;
        }

        @Deprecated
        static AtifView randomAtifView() {
            final AtifView atifView = new AtifView();
            // ~30 fps, 6 seconds video, so it should not be too much
            atifView.A = GTUtil.positiveRandom(50);
            atifView.T = GTUtil.randomInRange(50, 100);
            atifView.I = GTUtil.randomInRange(100, 150);
            atifView.F = GTUtil.randomInRange(150, 200);
            return atifView;
        }

        static Coach coach(String coachId, String username, String password, String fullName, String token, List<Store> stores) {
            final Coach coach = new Coach();
            coach.id = coachId;
            coach.username = username;
            coach.password = password;
            coach.name = fullName;
            coach.token = token;
            coach.avatarUrl = "http://www.taovh.com/images/designer-photo.jpg";
            coach.worksAt = stores;
            return coach;
        }
    }
}
