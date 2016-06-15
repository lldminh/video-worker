package com.golftec.teaching.videoUtil.annotation;

import com.golftec.teaching.videoUtil.history.ToolBoardHistory;
import com.golftec.teaching.videoUtil.util.ToolBoardEvent;
import com.golftec.teaching.videoUtil.util.ToolType;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class ToolBoard {
    //    public GraphicsContext gc = null;
    public Button lineTool = null;
    public Button ovalTool = null;
    public Button circelTool = null;
    public Button arrowTool = null;
    public Button recTool = null;
    public Button squareTool = null;
    public Button angleTool = null;
    public Button backTool = null;
    public Button refreshTool = null;
    public Button eraseTool = null;
    public Button constrantLineTool = null;
    public Button constrantArrowTool = null;
    public Button pencilTool = null;
    public Button red = null;
    public Button yellow = null;
    public Button green = null;
    public Button blue = null;
    public Button cursorTool = null;

    ToolType currentTool = null;

    private IToolBoard iToolBoard = null;

    private ToolBoardHistory toolBoardHistory = null;

    private boolean isRecording = false;

    public ToolBoard(IToolBoard iToolBoard) {
        toolBoardHistory = new ToolBoardHistory(this);
        this.iToolBoard = iToolBoard;
    }

    public void startRecord() {
        this.isRecording = true;
        this.toolBoardHistory.startRecord();
    }

    public void stopRecordAndSave(String filePath, String fileName) {
        this.isRecording = false;
        this.toolBoardHistory.serialize(filePath, fileName);
    }

    public ToolBoardHistory getToolBoardHistory() {
        return this.toolBoardHistory;
    }

    public void setToolBoardHistory(ToolBoardHistory toolBoardHistory) {
        this.toolBoardHistory = toolBoardHistory;
    }

    public void showHistory(ToolBoardHistory toolBoardHistory) {
        this.toolBoardHistory = toolBoardHistory;
        this.toolBoardHistory.setToolBoard(this);
        this.toolBoardHistory.showHistory();
    }

    public void addStandByHistory() {
        ToolBoardEvent toolBoardEvent = new ToolBoardEvent();
        toolBoardEvent.toolType = currentTool;

        toolBoardHistory.addStandby(toolBoardEvent);
    }

    public void cleanHistory() {
        this.toolBoardHistory.cleanHistory();
    }

    public void cancelShowingHistory() {
        this.toolBoardHistory.cancelShowHistory();
    }

    public void standByHistory() {
        this.toolBoardHistory.pause();
    }

    public void continueHistory() {
        this.toolBoardHistory.resumeHistory();
    }

    public void init() {
        if (this.lineTool != null) {
            this.lineTool.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            try {
                                consumeMouseEvent(ToolType.LINE, e);
                            } catch (Exception ex) {
                            }
                        }
                    });
        }

        if (this.ovalTool != null) {
            this.ovalTool.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            try {
                                consumeMouseEvent(ToolType.OVAL, e);

                            } catch (Exception ex) {
                            }
                        }
                    });
        }

        if (this.arrowTool != null) {
            this.arrowTool.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            try {
                                consumeMouseEvent(ToolType.ARROW, e);
                            } catch (Exception ex) {
                            }
                        }
                    });
        }

        if (this.circelTool != null) {
            this.circelTool.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            try {
                                consumeMouseEvent(ToolType.CIRCLE, e);

                            } catch (Exception ex) {
                            }
                        }
                    });
        }


        if (this.recTool != null) {
            this.recTool.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            try {

                                consumeMouseEvent(ToolType.RECTANGLE, e);
                            } catch (Exception ex) {
                            }
                        }
                    });
        }


        if (this.squareTool != null) {
            this.squareTool.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            try {
                                consumeMouseEvent(ToolType.SQUARE, e);
                            } catch (Exception ex) {
                            }
                        }
                    });

        }

        if (this.angleTool != null) {
            this.angleTool.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            try {
                                consumeMouseEvent(ToolType.ANGLE, e);

                            } catch (Exception ex) {
                            }
                        }
                    });
        }


        if (this.constrantLineTool != null) {
            this.constrantLineTool.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            try {
                                consumeMouseEvent(ToolType.CONSTRANT_LINE, e);
                            } catch (Exception ex) {
                            }
                        }
                    });
        }


        if (this.constrantArrowTool != null) {
            this.constrantArrowTool.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            try {
                                consumeMouseEvent(ToolType.CONSTRANT_ARROW, e);

                            } catch (Exception ex) {
                            }
                        }
                    });

        }

        if (this.eraseTool != null) {
            this.eraseTool.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            try {
                                consumeMouseEvent(ToolType.ERASE, e);
                            } catch (Exception ex) {
                            }
                        }
                    });
        }


        if (this.pencilTool != null) {
            this.pencilTool.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            try {
                                consumeMouseEvent(ToolType.PENCIL, e);
                            } catch (Exception ex) {
                            }
                        }
                    });
        }


        if (this.cursorTool != null) {
            this.cursorTool.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            try {

                                consumeMouseEvent(ToolType.CURSOR, e);

                            } catch (Exception ex) {
                            }
                        }
                    });
        }


        if (this.blue != null) {
            this.blue.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            try {
                                consumeMouseEvent(ToolType.BLUE, e);

                            } catch (Exception ex) {
                            }
                        }
                    });
        }


        if (this.yellow != null) {
            this.yellow.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            try {
                                consumeMouseEvent(ToolType.YELLOW, e);
                            } catch (Exception ex) {
                            }
                        }
                    });
        }


        if (this.green != null) {
            this.green.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            try {
                                consumeMouseEvent(ToolType.GREEN, e);
                            } catch (Exception ex) {
                            }
                        }
                    });
        }

        if (this.red != null) {
            this.red.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            try {
                                consumeMouseEvent(ToolType.RED, e);
                            } catch (Exception ex) {
                            }
                        }
                    });
        }


        if (this.backTool != null) {
            this.backTool.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            try {
                                consumeMouseEvent(ToolType.BACK, e);
                            } catch (Exception ex) {
                            }
                        }
                    });
        }


        if (this.refreshTool != null) {
            this.refreshTool.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            try {
                                consumeMouseEvent(ToolType.REFRESH, e);
                            } catch (Exception ex) {
                            }
                        }
                    });
        }

    }

    public void consumeMouseEvent(ToolType type, MouseEvent mouseEvent) {
        if (type == null) {
            return;
        }

        if (this.isRecording) {
            ToolBoardEvent toolBoardEvent = new ToolBoardEvent();
            toolBoardEvent.toolType = type;
            toolBoardHistory.add(toolBoardEvent);
        }

        currentTool = type;

        switch (type) {
            case EMPTY: {
                iToolBoard.consumeEmpty();
                break;
            }
            case ANGLE: {
                iToolBoard.consumeAngle();
                break;
            }
            case ARROW: {
                iToolBoard.consumeArrow();
                break;
            }
            case CONSTRANT_ARROW: {
                iToolBoard.consumeConstrantArrow();
                break;
            }
            case RECTANGLE: {
                iToolBoard.consumeRectangle();
                break;
            }
            case BLUE: {
                iToolBoard.consumeBlue();
                break;
            }
            case CIRCLE: {
                iToolBoard.consumeCircle();
                break;
            }
            case CONSTRANT_LINE: {
                iToolBoard.consumeConstrantLine();
                break;
            }
            case CURSOR: {
                iToolBoard.consumeCursor();
                break;
            }
            case GREEN: {
                iToolBoard.consumeGreen();
                break;
            }
            case LINE: {
                iToolBoard.consumeLine();
                break;
            }
            case OVAL: {
                iToolBoard.consumeOval();
                break;
            }
            case PENCIL: {
                iToolBoard.consumePencil();
                break;
            }
            case RED: {
                iToolBoard.consumeRed();
                break;
            }
            case SQUARE: {
                iToolBoard.consumeSquare();
                break;
            }
            case YELLOW: {
                iToolBoard.consumeYellow();
                break;
            }
            case ERASE: {
                iToolBoard.consumeErase();
                break;
            }
            case BACK: {
                iToolBoard.consumeBack();
                break;
            }
            case REFRESH: {
                iToolBoard.consumeRefresh();
                break;
            }
        }
    }
}
