package io.znz.jsite.visa.web;

import io.znz.jsite.base.BaseController;
import io.znz.jsite.base.bean.PageFilter;
import io.znz.jsite.base.bean.ResultObject;
import io.znz.jsite.visa.bean.Flight;
import io.znz.jsite.visa.bean.Hotel;
import io.znz.jsite.visa.form.FilterValueForm;
import io.znz.jsite.visa.service.HotelService;

import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.dao.IDbDao;

/**
 * Created by Chaly on 2017/3/7.
 */
@Controller
@RequestMapping("visa/hotel")
public class HotelController extends BaseController {
	@Autowired
	private HotelService hotelService;
	@Autowired
	protected IDbDao dbDao;

	@RequestMapping(value = "list")
	@ResponseBody
	public Object list(@RequestBody(required = false) PageFilter filter) {
		if (filter == null)
			filter = new PageFilter();
		Pageable pageable = filter.getPageable();
		Criterion[] filters = filter.getFilter(Flight.class);
		return hotelService.search(pageable, filters);
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Object save(Hotel hotel) {
		return ResultObject.success(hotelService.save(hotel));
	}

	@RequestMapping(value = "del/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Object del(@PathVariable int id) {
		hotelService.delete(id);
		return ResultObject.success();
	}

	@RequestMapping(value = "json")
	@ResponseBody
	public Object json(@RequestParam(required = false) String filter) {
		return hotelService.findByFilter(filter);
	}

	@RequestMapping(value = "arricity")
	@ResponseBody
	public Object arricity(String arricity) {
		if (Util.isEmpty(arricity)) {
			return null;
		}
		return hotelService.findByCity(arricity);
	}

	@RequestMapping(value = "arricityfilter")
	@ResponseBody
	public Object arricity(FilterValueForm form) {
		if (Util.isEmpty(form.getToCity())) {
			return null;
		}
		return hotelService.findByCityFilter(form.getToCity(), form);
	}

}