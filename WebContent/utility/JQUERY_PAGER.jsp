<tfoot>
<tr>
	<th colspan="20" id="pager" class="pager">
		<img src="<%=request.getContextPath()%>/jquery/addons/pager/icons/first.png" class="first" alt="First" />
		<img src="<%=request.getContextPath()%>/jquery/addons/pager/icons/prev.png" class="prev" alt="Prev" />
		<span class="pagedisplay"></span> <!-- this can be any element, including an input -->
		<img src="<%=request.getContextPath()%>/jquery/addons/pager/icons/next.png" class="next" alt="Next" />
		<img src="<%=request.getContextPath()%>/jquery/addons/pager/icons/last.png" class="last" alt="Last" />
		<select class="pagesize" title="Select page size">
			<option value="100">100</option>
			<option value="200">200</option>
			<option value="300">300</option>
			<option value="400">400</option>
		</select>
		<select class="gotoPage" title="Select page number"></select>
	</th>
</tr>
</tfoot> 