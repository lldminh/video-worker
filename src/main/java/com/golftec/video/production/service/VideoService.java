package com.golftec.video.production.service;

import com.golftec.teaching.common.GTUtil;
import com.golftec.teaching.model.lesson.TelestrationVideo;
import com.golftec.teaching.videoUtil.VideoFactoryEx;
import com.golftec.video.production.common.GTServerConstant;
import com.golftec.video.production.common.GTVideoProductionUtil;
import com.golftec.video.production.data.ComposeStatus;
import com.golftec.video.production.data.ServerStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

/**
 * Created by hoang on 2015-07-12.
 */
public final class VideoService {

    private static final Logger log = LoggerFactory.getLogger(VideoService.class);

    public static boolean composeTelestrationVideo(String telestrationId, String telestrationJsonFileUrl, String telestrationWavFileUrl) {
        log.info("Thread [{}]: Composing video for telestration {}", Thread.currentThread().getId(), telestrationId);

        try {
            GTVideoProductionUtil.downloadTelestrationJsonFile(telestrationJsonFileUrl, telestrationId);
            GTVideoProductionUtil.downloadTelestrationWavFile(telestrationWavFileUrl, telestrationId);
            Optional<TelestrationVideo> opTelestration = GTVideoProductionUtil.loadTelestrationFromJsonFile(telestrationId);
            TelestrationVideo telestrationVideo = opTelestration.get();

            final Path finalVideoFilePath = GTVideoProductionUtil.constructTelestrationFinalVideoFilePath(telestrationId);
            final Path telestrationFinalVideoFolder = finalVideoFilePath.getParent().resolve(GTServerConstant.TELESTRATION_VIDEO_OUTPUT_DIR);

            //reset soundfilepath
            telestrationVideo.setSoundFilePath(GTVideoProductionUtil.constructTelestrationWavFilePath(telestrationId).toString());

            log.info("composeTelestrationVideo check-mark 1");
            VideoFactoryEx videoFactoryEx = new VideoFactoryEx(telestrationVideo);
            videoFactoryEx.setOutPutFolder(telestrationFinalVideoFolder.toString() + File.separator);
            videoFactoryEx.setNumberOfThreads(GTServerConstant.ConfigOption.VideoFactoryNumberOfThreads());

            log.info("composeTelestrationVideo check-mark 2");
            final boolean isVideoFactoryProcessOk = videoFactoryEx.start(true);
            log.info("composeTelestrationVideo check-mark 3. {}", isVideoFactoryProcessOk);

            if (!isVideoFactoryProcessOk) {
                log.warn("composeTelestrationVideo failed. {}", telestrationId);
                GTVideoProductionUtil.updateTelestrationStatus(telestrationId, ComposeStatus.Fail.status);
                ServerStatus.setFree();
                return false;
            }

            final Path output = telestrationFinalVideoFolder.resolve("output.mp4");
            if (output.toFile().exists()) {
                if (output.toFile().length() > GTServerConstant.ConfigOption.MinSizeOutputAccepted()) {
                    log.info("Copying output.mp4 {}, {}", output, finalVideoFilePath);
                    Files.copy(output, finalVideoFilePath, StandardCopyOption.REPLACE_EXISTING);
                } else {
                    log.info("File size after compose about 0 bytes NOT ACCEPTED, status of this telestration will be set fail: {}", telestrationId);
                }
                Path video_output_path = Paths.get(GTServerConstant.ConfigOption.DataDir(), GTServerConstant.TELESTRATIONS_DIR_NAME, telestrationId, GTServerConstant.TELESTRATION_VIDEO_OUTPUT_DIR);
                log.info("Delete folder video-output {}", video_output_path);
                GTUtil.deleteFileSafely(video_output_path);
            } else {
                final Path out = telestrationFinalVideoFolder.resolve("out.mp4");
                if (out.toFile().exists()) {
                    log.info("Copying out.mp4 {}, {}", out, finalVideoFilePath);
                    Files.copy(out, finalVideoFilePath, StandardCopyOption.REPLACE_EXISTING);

                    Path video_output_path = Paths.get(GTServerConstant.ConfigOption.DataDir(), GTServerConstant.TELESTRATIONS_DIR_NAME, telestrationId, GTServerConstant.TELESTRATION_VIDEO_OUTPUT_DIR);
                    log.info("Delete folder video-output {}", video_output_path);
                    GTUtil.deleteFileSafely(video_output_path);
                }
            }

            if (finalVideoFilePath.toFile().exists()) {
                log.info("DONE Composing video for telestration {}", telestrationId);
                GTVideoProductionUtil.updateTelestrationStatus(telestrationId, ComposeStatus.Succeed.status);
                try {
                    log.info("Delete .wav and .json {} after composed", telestrationId);
                    GTUtil.deleteFileSafely(GTVideoProductionUtil.constructTelestrationWavFilePath(telestrationId));
                    GTUtil.deleteFileSafely(GTVideoProductionUtil.constructTelestrationJsonFilePath(telestrationId));
                } catch (Exception e) {
                    log.info("Error when delete .wav or .json {} after composed ", telestrationId);
                }
                ServerStatus.setFree();
                return true;
            } else {
                log.info("No final file found after composing telestration video {}", telestrationId);
                GTVideoProductionUtil.updateTelestrationStatus(telestrationId, ComposeStatus.Fail.status);
                ServerStatus.setFree();
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
