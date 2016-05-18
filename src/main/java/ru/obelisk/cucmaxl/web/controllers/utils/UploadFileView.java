package ru.obelisk.cucmaxl.web.controllers.utils;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.obelisk.database.models.entity.UploadFile;

@Data
@EqualsAndHashCode(callSuper=false)
public class UploadFileView extends UploadFile {
	private static final long serialVersionUID = -2504333197606503773L;
	private MultipartFile uploadedfile;
}