/*
 * Created 2014 by Tomas Mikula.
 *
 * The author dedicates this file to the public domain.
 */

package org.mapleaf.cointda.modules.note.richtextfx;

import com.dlsc.workbenchfx.Workbench;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.GenericStyledArea;
import org.fxmisc.richtext.StyledTextArea;
import org.fxmisc.richtext.TextExt;
import org.fxmisc.richtext.model.*;
import org.reactfx.SuspendableNo;
import org.reactfx.util.Either;
import org.reactfx.util.Tuple2;

import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static org.fxmisc.richtext.model.TwoDimensional.Bias.Backward;
import static org.fxmisc.richtext.model.TwoDimensional.Bias.Forward;

public class RichTextView extends AnchorPane {
    private static final String RTFX_DIR = createDir();
    // the saved/loaded files and their format are arbitrary and may change across versions
    private static final String RTFX_FILE_EXTENSION = ".rtfx";
    private final Workbench workbench;
    private final String fileName;
    private final TextOps<String, TextStyle> styledTextOps = SegmentOps.styledTextOps();
    private final LinkedImageOps<TextStyle> linkedImageOps = new LinkedImageOps<>();

    private final GenericStyledArea<ParStyle, Either<String, LinkedImage>, TextStyle> area =
            new GenericStyledArea<>(
                    ParStyle.EMPTY,                                                 // default paragraph style
                    (paragraph, style) -> paragraph.setStyle(style.toCss()),        // paragraph style setter

                    TextStyle.EMPTY.updateFontSize(12).updateFontFamily("Serif").updateTextColor(Color.BLACK),  // default segment style
                    styledTextOps._or(linkedImageOps, (s1, s2) -> Optional.empty()),                            // segment operations
                    seg -> createNode(seg, (text, style) -> text.setStyle(style.toCss())));                     // Node creator and segment style setter
    private final SuspendableNo updatingToolbar = new SuspendableNo();

    {
        area.setWrapText(true);
        area.setStyleCodecs(
                ParStyle.CODEC,
                Codec.styledSegmentCodec(Codec.eitherCodec(Codec.STRING_CODEC, LinkedImage.codec()), TextStyle.CODEC));
        area.setParagraphGraphicFactory( new BulletFactory( area ) );
    }


    public RichTextView(Workbench workbench, String fileName) {
        this.workbench = workbench;
        this.fileName = fileName;
//        Button loadBtn = createButton("loadfile", this::loadDocument,
//                "Load document.\n\n" +
//                        "Note: the demo will load only previously-saved \"" + RTFX_FILE_EXTENSION + "\" files. " +
//                        "This file format is abitrary and may change across versions.");
        Button saveBtn = createButton("savefile", this::saveDocument,"保存文档");
//        CheckBox wrapToggle = new CheckBox("自动换行");
//        wrapToggle.setSelected(true);
        area.wrapTextProperty().set(true);
//        Button undoBtn = createButton("undo", area::undo, "撤消");
//        Button redoBtn = createButton("redo", area::redo, "重做");
//        Button cutBtn = createButton("cut", area::cut, "Cut");
//        Button copyBtn = createButton("copy", area::copy, "Copy");
//        Button pasteBtn = createButton("paste", area::paste, "Paste");
        Button boldBtn = createButton("bold", this::toggleBold, "粗体");
        Button italicBtn = createButton("italic", this::toggleItalic, "斜体");
        Button underlineBtn = createButton("underline", this::toggleUnderline, "下划线");
        Button strikeBtn = createButton("strikethrough", this::toggleStrikethrough, "删除线");
//        Button insertImageBtn = createButton("insertimage", this::insertImage, "Insert Image");
        Button increaseIndentBtn = createButton("increaseIndent", this::increaseIndent, "增加缩进量");
        Button decreaseIndentBtn = createButton("decreaseIndent", this::decreaseIndent, "减少缩进量");
//        ToggleGroup alignmentGrp = new ToggleGroup();
//        ToggleButton alignLeftBtn = createToggleButton(alignmentGrp, "align-left", this::alignLeft, "Align left");
//        ToggleButton alignCenterBtn = createToggleButton(alignmentGrp, "align-center", this::alignCenter, "Align center");
//        ToggleButton alignRightBtn = createToggleButton(alignmentGrp, "align-right", this::alignRight, "Align right");
//        ToggleButton alignJustifyBtn = createToggleButton(alignmentGrp, "align-justify", this::alignJustify, "Justify");
        ColorPicker paragraphBackgroundPicker = new ColorPicker();
        ComboBox<Integer> sizeCombo = new ComboBox<>(FXCollections.observableArrayList(5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 16, 18, 20, 22, 24, 28, 32, 36, 40, 48, 56, 64, 72));
        sizeCombo.getSelectionModel().select(Integer.valueOf(12));
        sizeCombo.setTooltip(new Tooltip("字体"));
        ComboBox<String> familyCombo = new ComboBox<>(FXCollections.observableList(Font.getFamilies()));
        familyCombo.getSelectionModel().select("Serif");
        familyCombo.setTooltip(new Tooltip("字体系列"));
        ColorPicker textColorPicker = new ColorPicker(Color.BLACK);
        ColorPicker backgroundColorPicker = new ColorPicker();

        paragraphBackgroundPicker.setTooltip(new Tooltip("段落背景色"));
        textColorPicker.setTooltip(new Tooltip("字体颜色"));
        backgroundColorPicker.setTooltip(new Tooltip("字体背景色"));

        paragraphBackgroundPicker.valueProperty().addListener((o, old, color) -> updateParagraphBackground(color));
        sizeCombo.setOnAction(evt -> updateFontSize(sizeCombo.getValue()));
        familyCombo.setOnAction(evt -> updateFontFamily(familyCombo.getValue()));
        textColorPicker.valueProperty().addListener((o, old, color) -> updateTextColor(color));
        backgroundColorPicker.valueProperty().addListener((o, old, color) -> updateBackgroundColor(color));

//        undoBtn.disableProperty().bind(area.undoAvailableProperty().map(x -> !x));
//        redoBtn.disableProperty().bind(area.redoAvailableProperty().map(x -> !x));

        BooleanBinding selectionEmpty = new BooleanBinding() {
            { bind(area.selectionProperty()); }

            @Override
            protected boolean computeValue() {
                return area.getSelection().getLength() == 0;
            }
        };

//        cutBtn.disableProperty().bind(selectionEmpty);
//        copyBtn.disableProperty().bind(selectionEmpty);

        area.beingUpdatedProperty().addListener((o, old, beingUpdated) -> {
            if(!beingUpdated) {
                boolean bold, italic, underline, strike;
                Integer fontSize;
                String fontFamily;
                Color textColor;
                Color backgroundColor;

                IndexRange selection = area.getSelection();
                if(selection.getLength() != 0) {
                    StyleSpans<TextStyle> styles = area.getStyleSpans(selection);
                    bold = styles.styleStream().anyMatch(s -> s.bold.orElse(false));
                    italic = styles.styleStream().anyMatch(s -> s.italic.orElse(false));
                    underline = styles.styleStream().anyMatch(s -> s.underline.orElse(false));
                    strike = styles.styleStream().anyMatch(s -> s.strikethrough.orElse(false));
                    int[] sizes = styles.styleStream().mapToInt(s -> s.fontSize.orElse(-1)).distinct().toArray();
                    fontSize = sizes.length == 1 ? sizes[0] : -1;
                    String[] families = styles.styleStream().map(s -> s.fontFamily.orElse(null)).distinct().toArray(String[]::new);
                    fontFamily = families.length == 1 ? families[0] : null;
                    Color[] colors = styles.styleStream().map(s -> s.textColor.orElse(null)).distinct().toArray(Color[]::new);
                    textColor = colors.length == 1 ? colors[0] : null;
                    Color[] backgrounds = styles.styleStream().map(s -> s.backgroundColor.orElse(null)).distinct().toArray(i -> new Color[i]);
                    backgroundColor = backgrounds.length == 1 ? backgrounds[0] : null;
                } else {
                    int p = area.getCurrentParagraph();
                    int col = area.getCaretColumn();
                    TextStyle style = area.getStyleAtPosition(p, col);
                    bold = style.bold.orElse(false);
                    italic = style.italic.orElse(false);
                    underline = style.underline.orElse(false);
                    strike = style.strikethrough.orElse(false);
                    fontSize = style.fontSize.orElse(-1);
                    fontFamily = style.fontFamily.orElse(null);
                    textColor = style.textColor.orElse(null);
                    backgroundColor = style.backgroundColor.orElse(null);
                }

                int startPar = area.offsetToPosition(selection.getStart(), Forward).getMajor();
                int endPar = area.offsetToPosition(selection.getEnd(), Backward).getMajor();
                List<Paragraph<ParStyle, Either<String, LinkedImage>, TextStyle>> pars = area.getParagraphs().subList(startPar, endPar + 1);

                @SuppressWarnings("unchecked")
                Optional<TextAlignment>[] alignments = pars.stream().map(p -> p.getParagraphStyle().alignment).distinct().toArray(Optional[]::new);
                Optional<TextAlignment> alignment = alignments.length == 1 ? alignments[0] : Optional.empty();

                @SuppressWarnings("unchecked")
                Optional<Color>[] paragraphBackgrounds = pars.stream().map(p -> p.getParagraphStyle().backgroundColor).distinct().toArray(Optional[]::new);
                Optional<Color> paragraphBackground = paragraphBackgrounds.length == 1 ? paragraphBackgrounds[0] : Optional.empty();

                updatingToolbar.suspendWhile(() -> {
                    if(bold) {
                        if(!boldBtn.getStyleClass().contains("pressed")) {
                            boldBtn.getStyleClass().add("pressed");
                        }
                    } else {
                        boldBtn.getStyleClass().remove("pressed");
                    }

                    if(italic) {
                        if(!italicBtn.getStyleClass().contains("pressed")) {
                            italicBtn.getStyleClass().add("pressed");
                        }
                    } else {
                        italicBtn.getStyleClass().remove("pressed");
                    }

                    if(underline) {
                        if(!underlineBtn.getStyleClass().contains("pressed")) {
                            underlineBtn.getStyleClass().add("pressed");
                        }
                    } else {
                        underlineBtn.getStyleClass().remove("pressed");
                    }

                    if(strike) {
                        if(!strikeBtn.getStyleClass().contains("pressed")) {
                            strikeBtn.getStyleClass().add("pressed");
                        }
                    } else {
                        strikeBtn.getStyleClass().remove("pressed");
                    }

//                    if(alignment.isPresent()) {
//                        TextAlignment al = alignment.get();
//                        switch(al) {
//                            case LEFT: alignmentGrp.selectToggle(alignLeftBtn); break;
//                            case CENTER: alignmentGrp.selectToggle(alignCenterBtn); break;
//                            case RIGHT: alignmentGrp.selectToggle(alignRightBtn); break;
//                            case JUSTIFY: alignmentGrp.selectToggle(alignJustifyBtn); break;
//                        }
//                    } else {
//                        alignmentGrp.selectToggle(null);
//                    }

                    paragraphBackgroundPicker.setValue(paragraphBackground.orElse(null));

                    if(fontSize != -1) {
                        sizeCombo.getSelectionModel().select(fontSize);
                    } else {
                        sizeCombo.getSelectionModel().clearSelection();
                    }

                    if(fontFamily != null) {
                        familyCombo.getSelectionModel().select(fontFamily);
                    } else {
                        familyCombo.getSelectionModel().clearSelection();
                    }

                    if(textColor != null) {
                        textColorPicker.setValue(textColor);
                    }

                    backgroundColorPicker.setValue(backgroundColor);
                });
            }
        });

        ToolBar toolBar1 = new ToolBar(
                saveBtn,  new Separator(Orientation.VERTICAL),
                boldBtn, italicBtn, underlineBtn, strikeBtn, new Separator(Orientation.VERTICAL),
                increaseIndentBtn, decreaseIndentBtn, new Separator(Orientation.VERTICAL),
                paragraphBackgroundPicker, new Separator(Orientation.VERTICAL),
                sizeCombo, familyCombo, textColorPicker, backgroundColorPicker
        );
        
        //ToolBar toolBar2 = new ToolBar(sizeCombo, familyCombo, textColorPicker, backgroundColorPicker);

        VirtualizedScrollPane<GenericStyledArea<ParStyle, Either<String, LinkedImage>, TextStyle>> vsPane = new VirtualizedScrollPane<>(area);
        VBox vbox = new VBox();
        VBox.setVgrow(vsPane, Priority.ALWAYS);
        vbox.getChildren().addAll(toolBar1, vsPane);

        this.getStylesheets().add(RichTextView.class.getResource("rich-text.css").toExternalForm());
        getChildren().addAll(vbox);
        setTopAnchor(vbox, 0.0);
        setLeftAnchor(vbox, 0.0);
        setRightAnchor(vbox, 0.0);
        setBottomAnchor(vbox, 0.0);

        area.requestFocus();
        loadDocument();
    }

    private static String createDir(){
        String dir = null;
        String cointdaDir = System.getProperty("user.home");
        File f1 = new File(cointdaDir, "/cointda");
        if(f1.exists() && f1.isDirectory()){
            File f2 = new File(f1, "/note");
            if(!f2.exists() || !f2.isDirectory()){
                f2.mkdir();
            }
            dir = f2.getAbsolutePath();
        }else{
            if(f1.mkdir()){
                File f2 = new File(f1, "/note");
                if(f2.mkdir()){
                    dir = f2.getAbsolutePath();
                }
            }
        }

        return dir;
    }

    private Node createNode(StyledSegment<Either<String, LinkedImage>, TextStyle> seg,
                            BiConsumer<? super TextExt, TextStyle> applyStyle) {
        return seg.getSegment().unify(
                text -> StyledTextArea.createStyledTextNode(text, seg.getStyle(), applyStyle),
                LinkedImage::createNode
        );
    }

    private Button createButton(String styleClass, Runnable action, String toolTip) {
        Button button = new Button();
        button.getStyleClass().add(styleClass);
        button.setOnAction(evt -> {
            action.run();
            area.requestFocus();
        });
        button.setPrefWidth(25);
        button.setPrefHeight(25);
        if (toolTip != null) {
            button.setTooltip(new Tooltip(toolTip));
        }
        return button;
    }

    private ToggleButton createToggleButton(ToggleGroup grp, String styleClass, Runnable action, String toolTip) {
        ToggleButton button = new ToggleButton();
        button.setToggleGroup(grp);
        button.getStyleClass().add(styleClass);
        button.setOnAction(evt -> {
            action.run();
            area.requestFocus();
        });
        button.setPrefWidth(25);
        button.setPrefHeight(25);
        if (toolTip != null) {
            button.setTooltip(new Tooltip(toolTip));
        }
        return button;
    }

    private void toggleBold() {
        updateStyleInSelection(spans -> TextStyle.bold(!spans.styleStream().allMatch(style -> style.bold.orElse(false))));
    }

    private void toggleItalic() {
        updateStyleInSelection(spans -> TextStyle.italic(!spans.styleStream().allMatch(style -> style.italic.orElse(false))));
    }

    private void toggleUnderline() {
        updateStyleInSelection(spans -> TextStyle.underline(!spans.styleStream().allMatch(style -> style.underline.orElse(false))));
    }

    private void toggleStrikethrough() {
        updateStyleInSelection(spans -> TextStyle.strikethrough(!spans.styleStream().allMatch(style -> style.strikethrough.orElse(false))));
    }

    private void alignLeft() {
        updateParagraphStyleInSelection(ParStyle.alignLeft());
    }

    private void alignCenter() {
        updateParagraphStyleInSelection(ParStyle.alignCenter());
    }

    private void alignRight() {
        updateParagraphStyleInSelection(ParStyle.alignRight());
    }

    private void alignJustify() {
        updateParagraphStyleInSelection(ParStyle.alignJustify());
    }

    private void loadDocument() {
        File file = new File(RTFX_DIR, fileName + RTFX_FILE_EXTENSION);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        area.clear();
        load(file);
    }

    private void load(File file) {
        if(area.getStyleCodecs().isPresent()) {
            Tuple2<Codec<ParStyle>, Codec<StyledSegment<Either<String, LinkedImage>, TextStyle>>> codecs = area.getStyleCodecs().get();
            Codec<StyledDocument<ParStyle, Either<String, LinkedImage>, TextStyle>>
                codec = ReadOnlyStyledDocument.codec(codecs._1, codecs._2, area.getSegOps());

            try {
                FileInputStream fis = new FileInputStream(file);
                DataInputStream dis = new DataInputStream(fis);
                StyledDocument<ParStyle, Either<String, LinkedImage>, TextStyle> doc = codec.decode(dis);
                fis.close();

                if(doc != null) {
                    area.replaceSelection(doc);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveDocument() {
        File file = new File(RTFX_DIR, fileName + RTFX_FILE_EXTENSION);
        save(file);
    }

    private void save(File file) {
        StyledDocument<ParStyle, Either<String, LinkedImage>, TextStyle> doc = area.getDocument();

        // Use the Codec to save the document in a binary format
        area.getStyleCodecs().ifPresent(codecs -> {
            Codec<StyledDocument<ParStyle, Either<String, LinkedImage>, TextStyle>> codec =
                    ReadOnlyStyledDocument.codec(codecs._1, codecs._2, area.getSegOps());
            try {
                FileOutputStream fos = new FileOutputStream(file);
                DataOutputStream dos = new DataOutputStream(fos);
                codec.encode(dos, doc);
                fos.close();
            } catch (IOException fnfe) {
                fnfe.printStackTrace();
            }
        });
    }


    /**
     * Action listener which inserts a new image at the current caret position.
     */
    private void insertImage() {
        String initialDir = System.getProperty("user.dir");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Insert image");
        fileChooser.setInitialDirectory(new File(initialDir));
        File selectedFile = fileChooser.showOpenDialog(workbench.getScene().getWindow());
        if (selectedFile != null) {
            String imagePath = selectedFile.getAbsolutePath();
            imagePath = imagePath.replace('\\',  '/');
            ReadOnlyStyledDocument<ParStyle, Either<String, LinkedImage>, TextStyle> ros =
                    ReadOnlyStyledDocument.fromSegment(Either.right(new RealLinkedImage(imagePath)),
                                                       ParStyle.EMPTY, TextStyle.EMPTY, area.getSegOps());
            area.replaceSelection(ros);
        }
    }

    private void increaseIndent() {
        updateParagraphStyleInSelection( ps -> ps.increaseIndent() );
    }

    private void decreaseIndent() {
        updateParagraphStyleInSelection( ps -> ps.decreaseIndent() );
    }

    private void updateStyleInSelection(Function<StyleSpans<TextStyle>, TextStyle> mixinGetter) {
        IndexRange selection = area.getSelection();
        if(selection.getLength() != 0) {
            StyleSpans<TextStyle> styles = area.getStyleSpans(selection);
            TextStyle mixin = mixinGetter.apply(styles);
            StyleSpans<TextStyle> newStyles = styles.mapStyles(style -> style.updateWith(mixin));
            area.setStyleSpans(selection.getStart(), newStyles);
        }
    }

    private void updateStyleInSelection(TextStyle mixin) {
        IndexRange selection = area.getSelection();
        if (selection.getLength() != 0) {
            StyleSpans<TextStyle> styles = area.getStyleSpans(selection);
            StyleSpans<TextStyle> newStyles = styles.mapStyles(style -> style.updateWith(mixin));
            area.setStyleSpans(selection.getStart(), newStyles);
        }
    }

    private void updateParagraphStyleInSelection(Function<ParStyle, ParStyle> updater) {
        IndexRange selection = area.getSelection();
        int startPar = area.offsetToPosition(selection.getStart(), Forward).getMajor();
        int endPar = area.offsetToPosition(selection.getEnd(), Backward).getMajor();
        for(int i = startPar; i <= endPar; ++i) {
            Paragraph<ParStyle, Either<String, LinkedImage>, TextStyle> paragraph = area.getParagraph(i);
            area.setParagraphStyle(i, updater.apply(paragraph.getParagraphStyle()));
        }
    }

    private void updateParagraphStyleInSelection(ParStyle mixin) {
        updateParagraphStyleInSelection(style -> style.updateWith(mixin));
    }

    private void updateFontSize(Integer size) {
        if(!updatingToolbar.get()) {
            updateStyleInSelection(TextStyle.fontSize(size));
        }
    }

    private void updateFontFamily(String family) {
        if(!updatingToolbar.get()) {
            updateStyleInSelection(TextStyle.fontFamily(family));
        }
    }

    private void updateTextColor(Color color) {
        if(!updatingToolbar.get()) {
            updateStyleInSelection(TextStyle.textColor(color));
        }
    }

    private void updateBackgroundColor(Color color) {
        if(!updatingToolbar.get()) {
            updateStyleInSelection(TextStyle.backgroundColor(color));
        }
    }

    private void updateParagraphBackground(Color color) {
        if(!updatingToolbar.get()) {
            updateParagraphStyleInSelection(ParStyle.backgroundColor(color));
        }
    }
}
