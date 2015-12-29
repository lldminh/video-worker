package com.golftec.video.production.service;

import com.golftec.teaching.common.GTUtil;
import com.golftec.teaching.model.lesson.TelestrationVideo;
import com.golftec.teaching.videoUtil.VideoFactoryEx;
import com.golftec.video.production.common.GTServerConstant;
import com.golftec.video.production.common.GTServerUtil;
import com.golftec.video.production.model.ProcessingTelestration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

/**
 * Created by hoang on 2015-07-12.
 */
public final class VideoService {

    private static final Logger log = LoggerFactory.getLogger(VideoService.class);

    public static boolean composeTelestrationVideo(String lessonId, String telestrationId) {
        log.info("Thread [{}]: Composing video for lesson's telestration {}/{}", Thread.currentThread().getId(), lessonId, telestrationId);

        try {
            Optional<TelestrationVideo> opTelestration = GTServerUtil.loadTelestrationFromJsonFile(lessonId, telestrationId);
            TelestrationVideo telestrationVideo = opTelestration.get();
            boolean isAllLinkedVideosOk = isAllLinksOk(telestrationVideo.getVideoURLs());
            if (!isAllLinkedVideosOk) {
                log.warn("composeTelestrationVideo Some linked videos are not ok. {}/{}", lessonId, telestrationId);
                ProcessingTelestration.instance().remove(telestrationId);
                return false;
            }

            final Path finalVideoFilePath = GTServerUtil.constructTelestrationFinalVideoFilePath(lessonId, telestrationId);
            final Path telestrationFinalVideoFolder = finalVideoFilePath.getParent().resolve(GTServerConstant.TELESTRATION_VIDEO_OUTPUT_DIR);

            log.info("composeTelestrationVideo check-mark 1");
            VideoFactoryEx videoFactoryEx = new VideoFactoryEx(telestrationVideo);
            videoFactoryEx.setOutPutFolder(telestrationFinalVideoFolder.toString() + File.separator);
            videoFactoryEx.setNumberOfThreads(GTServerConstant.ConfigOption.VideoFactoryNumberOfThreads());

            log.info("composeTelestrationVideo check-mark 2");
            final boolean isVideoFactoryProcessOk = videoFactoryEx.start(true);
            log.info("composeTelestrationVideo check-mark 3. {}", isVideoFactoryProcessOk);

            if (!isVideoFactoryProcessOk) {
                log.warn("composeTelestrationVideo failed. {}/{}", lessonId, telestrationId);
                ProcessingTelestration.instance().remove(telestrationId);
                return false;
            }

            final Path output = telestrationFinalVideoFolder.resolve("output.mp4");
            if (output.toFile().exists()) {
                log.info("Copying output.mp4 {}, {}", output, finalVideoFilePath);
                Files.copy(output, finalVideoFilePath, StandardCopyOption.REPLACE_EXISTING);
            } else {
                final Path out = telestrationFinalVideoFolder.resolve("out.mp4");
                if (out.toFile().exists()) {
                    log.info("Copying out.mp4 {}, {}", out, finalVideoFilePath);
                    Files.copy(out, finalVideoFilePath, StandardCopyOption.REPLACE_EXISTING);
                }
            }

            if (finalVideoFilePath.toFile().exists()) {
                log.info("DONE Composing video for telestration {}", telestrationId);
                ProcessingTelestration.instance().remove(telestrationId);
                return true;
            } else {
                log.info("No final file found after composing telestration video {}", telestrationId);
                ProcessingTelestration.instance().remove(telestrationId);
                return false;
            }
        } catch (Exception e) {
            log.error("", e);
        }

        return false;
    }

    public static boolean isAllLinksOk(List<String> urls) {
        if (urls == null) {
            return false;
        }

        if (urls.size() == 0) {
            return true;
        }

        for (String url : urls) {
            boolean oneLinkOk = GTUtil.isLinkOk(url);
            if (!oneLinkOk) {
                return false;
            }
        }

        return true;
    }


}
