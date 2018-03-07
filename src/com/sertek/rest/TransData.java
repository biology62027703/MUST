package com.sertek.rest;

import java.util.*;


import javax.servlet.ServletContext;
import javax.ws.rs.core.MediaType;

import javax.ws.rs.Consumes;
//import javax.ws.rs.Resource;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import demo.rest.StudentBean;
import net.sf.json.*;

/**
 * 提供 REST Web Servcie 服務
 */

@Path("/connect")	// sets the path for this service
public class TransData {

	
	/**
	 * 接收資料
	 */
	@Path("/doRecieve.do")
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF8")
	//@Consumes("application/x-www-form-urlencoded")
	public List<HashMap> getClientMessage(@javax.ws.rs.core.Context ServletContext application,@QueryParam("param") String para){
		//CourtStateUtil courtStateUtil = (CourtStateUtil)application.getAttribute("courtStateUtil");
		List<HashMap> ret = new ArrayList<HashMap>();
		HashMap ret1 = new HashMap();
		ret1.put("status","SUCCESS");
		ret1.put("message","資料傳送成功");
		ret.add(ret1);
		
		return ret;
	}
	
	
	/**
	 * 更新資料
	 */
	@Path("/doUpdate.do")
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF8")
	//@Consumes("application/x-www-form-urlencoded")
	public List<HashMap> getClientMessage1(@javax.ws.rs.core.Context ServletContext application,@QueryParam("param") String para){
		//CourtStateUtil courtStateUtil = (CourtStateUtil)application.getAttribute("courtStateUtil");
		List<HashMap> ret = new ArrayList<HashMap>();
		HashMap ret1 = new HashMap();
		ret1.put("status","SUCCESS");
		ret1.put("message","資料傳送成功");
		ret.add(ret1);
		return ret;
	}
	
	/**
	 * 更新資料
	 */
	@Path("/doUpdate2.do")
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF8")
	//@Consumes("application/x-www-form-urlencoded")
	public List<HashMap> getClientMessage2(@javax.ws.rs.core.Context ServletContext application,@QueryParam("param") String para){
		//CourtStateUtil courtStateUtil = (CourtStateUtil)application.getAttribute("courtStateUtil");
		List<HashMap> ret = new ArrayList<HashMap>();
		HashMap ret1 = new HashMap();
		ret1.put("status","SUCCESS");
		ret1.put("message","資料傳送成功");
		ret.add(ret1);
		return ret;
	}
}
