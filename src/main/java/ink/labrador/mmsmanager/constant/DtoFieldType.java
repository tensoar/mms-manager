package ink.labrador.mmsmanager.constant;

import ink.labrador.mmsmanager.support.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DtoFieldType implements BaseEnum<Integer> {
    NORMAL(0, "非文件普通字段"),
    SingFilePart(1, "单个文件FilePart"),
    MultiFilePart(2, "多个文件List<FilePart>");
    private Integer value;
    private String description;
}
