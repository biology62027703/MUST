function Tag(szObjName, szDivName)
{
	if (szObjName == null || szDivName == null || document.getElementById(szDivName) != null)
	{
		return null;
	}
	this.tagArray = new Array();
	this.szObjName = szObjName;
	this.szDivName = szDivName;
}

function TagObject(szText, szHint, szClickScript, szReleaseScript, szMouseOutClass, szMouseOverClass, szExtraStyle)
{
	this.szText = szText;
	this.szHint = szHint;
	this.szClickScript = szClickScript;
	this.szReleaseScript = szReleaseScript;
	this.szMouseOutClass = szMouseOutClass;
	this.szMouseOverClass = szMouseOverClass;
	this.szExtraStyle = szExtraStyle;
	this.bClicked = false;
}

Tag.prototype.add = function(szText, szHint, szClickScript, szReleaseScript, szMouseOutClass, szMouseOverClass, szExtraStyle)
{
	this.tagArray.length++;
	this.tagArray[this.tagArray.length - 1] = new TagObject(szText, szHint, szClickScript, szReleaseScript, szMouseOutClass, szMouseOverClass, szExtraStyle);	
}

Tag.prototype.show = function()
{	
	if (document.getElementById(this.szName) != null)
	{
		return false;
	}

	document.writeln("<div id='"+this.szDivName+"'>");
	for (i = 0 ; i < this.tagArray.length ; ++i)
	{
		document.write("<div id='"+this.szDivName+i+"'");
		document.write("class=\""+this.tagArray[i].szMouseOutClass+"\" ");
		document.write("onMouseOut=\""+this.szObjName+".execute(this,'MouseOut');\" ");
		document.write("onMouseOver=\""+this.szObjName+".execute(this,'MouseOver');\" ");
		document.write("onClick=\""+this.szObjName+".execute(this,'Click');\" ");
		if (this.tagArray[i].szHint != "")
			document.write("title=\""+this.tagArray[i].szHint+"\" ");
		if (this.tagArray[i].szExtraStyle != "")
			document.write("style=\""+this.tagArray[i].szExtraStyle+"\" ");
		document.writeln(">"+this.tagArray[i].szText+"</div>");
	}
	document.writeln("</div>");
}

Tag.prototype.execute = function(objElement, szEvent)
{
	if (szEvent == "MouseOver")
	{
		if (!this.tagArray[objElement.id.slice(this.szDivName.length)].bClicked)
		{
			objElement.className=this.tagArray[objElement.id.slice(this.szDivName.length)].szMouseOverClass;
		}
	}
	else if (szEvent == "MouseOut")
	{
		if (!this.tagArray[objElement.id.slice(this.szDivName.length)].bClicked)
		{
			objElement.className=this.tagArray[objElement.id.slice(this.szDivName.length)].szMouseOutClass;
		}
	}
	else if (szEvent == "Click")
	{
		for (i = 0 ; i < this.tagArray.length ; ++i)
		{
			if (i == objElement.id.slice(this.szDivName.length))
			{
				if (this.tagArray[i].bClicked)
				{
					objElement.className = this.tagArray[i].szMouseOutClass;
					this.tagArray[i].bClicked = false;
					if (this.tagArray[i].szReleaseScript != null)
					{
						eval(this.tagArray[i].szReleaseScript);
					}
				}
				else
				{
					if (this.tagArray[i].szReleaseScript != null)
					{
						objElement.className = this.tagArray[i].szMouseOverClass;
						this.tagArray[i].bClicked = true;
					}
					eval(this.tagArray[i].szClickScript);
				}
			}
			else
			{
				if (this.tagArray[i].bClicked)
				{
					document.getElementById(this.szDivName+i).className = this.tagArray[i].szMouseOutClass;
					this.tagArray[i].bClicked = false;
				}
			}
		}
	}
}
