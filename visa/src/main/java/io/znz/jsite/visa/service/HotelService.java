package io.znz.jsite.visa.service;

import io.znz.jsite.base.BaseService;
import io.znz.jsite.base.HibernateDao;
import io.znz.jsite.util.StringUtils;
import io.znz.jsite.visa.bean.Hotel;
import io.znz.jsite.visa.dao.HotelDao;
import io.znz.jsite.visa.form.FilterValueForm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uxuexi.core.common.util.Util;

/**
 * Created by Chaly on 2017/4/11.
 */
@Service
@Transactional(readOnly = true)
public class HotelService extends BaseService<Hotel, Integer> {

	@Autowired
	private HotelDao hotelDao;

	public HibernateDao<Hotel, Integer> getEntityDao() {
		return hotelDao;
	}

	public List<Hotel> findByFilter(String filter) {
		List<Hotel> result;
		if (StringUtils.isNotBlank(filter))
			result = hotelDao.findByFilter(filter);
		else
			result = hotelDao.findAll();
		return result;
	}

	public List<Hotel> findByCity(String filter) {
		List<Hotel> result;
		if (StringUtils.isNotBlank(filter))
			result = hotelDao.findByCity(filter);
		else
			result = hotelDao.findAll();
		return result;
	}

	public List<Hotel> findByCityFilter(String toCity, FilterValueForm form) {

		List<Hotel> result = null;
		if (StringUtils.isNotBlank(toCity)) {
			String filterValue = form.getFilterValue();

			if (Util.isEmpty(filterValue)) {
				result = hotelDao.findByCity(toCity);
			} else {
				result = hotelDao.findByCityFilter(toCity, filterValue);
			}
		} else {
			//result = flightDao.findAll();
		}
		return result;
	}

}
