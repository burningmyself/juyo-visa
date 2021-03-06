/**
 * SchoolDto.java
 * io.znz.jsite.visa.simulator.dto
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package io.znz.jsite.visa.simulator.dto;

import java.util.Date;

public class SchoolDto {
	private Integer id;
	//开始建间
	private Date startDate;
	//结束建间
	private Date endDate;
	private String name;
	private String nameEN;

	private String country;//国籍
	private String province;//省份
	private String city;//城市

	private String zipCode;//邮编
	private String address;//住址街道号
	private String room;//小区楼号单元楼层房间号
	private String phone;//小区楼号单元楼层房间号

	private String addressEN;//住址街道号
	private String roomEN;//小区楼号单元楼层房间号

	private String specialty;//专业
	private String specialtyEN;//专业英文名

	private String degree;//学位
	private String degreeEN;//学位英文名

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameEN() {
		return nameEN;
	}

	public void setNameEN(String nameEN) {
		this.nameEN = nameEN;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getAddressEN() {
		return addressEN;
	}

	public void setAddressEN(String addressEN) {
		this.addressEN = addressEN;
	}

	public String getRoomEN() {
		return roomEN;
	}

	public void setRoomEN(String roomEN) {
		this.roomEN = roomEN;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getSpecialtyEN() {
		return specialtyEN;
	}

	public void setSpecialtyEN(String specialtyEN) {
		this.specialtyEN = specialtyEN;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getDegreeEN() {
		return degreeEN;
	}

	public void setDegreeEN(String degreeEN) {
		this.degreeEN = degreeEN;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
