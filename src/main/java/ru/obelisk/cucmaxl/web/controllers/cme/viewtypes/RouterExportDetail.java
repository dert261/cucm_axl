package ru.obelisk.cucmaxl.web.controllers.cme.viewtypes;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.obelisk.cucmaxl.database.models.entity.CucmAxlPort;
import ru.obelisk.cucmaxl.database.models.entity.cme.CmeRouter;
import ru.obelisk.cucmaxl.database.models.views.View;

@ToString
public class RouterExportDetail {
	@Getter
	@Setter
	@JsonView(View.RouterExportDetail.class)
	private String devicePool;
	
	@Getter
	@Setter
	@JsonView(View.RouterExportDetail.class)
	private CmeRouter source;
	
	@Getter
	@Setter
	@JsonView(View.RouterExportDetail.class)
	private CucmAxlPort destination;
	
	@Getter
	@Setter
	@JsonView(View.RouterExportDetail.class)
	private String linePartition;
	
	@Getter
	@Setter
	@JsonView(View.RouterExportDetail.class)
	private String phoneCss;
	
	@Getter
	@Setter
	@JsonView(View.RouterExportDetail.class)
	private String lineCss;
}
