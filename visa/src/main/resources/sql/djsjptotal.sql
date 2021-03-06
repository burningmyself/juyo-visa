/*djsjptotal_list_data*/
SELECT
	vnoj.id,
	vnoj.comId,
	c.comType,
	(
		SELECT
			COUNT(*)
		FROM
			visa_new_comebaby_jp p
    WHERE p.comId=vnoj.comId
	) AS sqscount,
	(
		SELECT
			COUNT(*)
		FROM
			visa_new_comebaby_jp_djs
	) AS djscount,
	vnoj.ordernumber,
	come.comFullName,
	vnoj.completedNumber,
	eee.fullName AS username,
	djs.landcomFullName,
	vnoj.headnum,
	vnoj.visatype,
	vnoj.senddate,
	vnoj.outdate,
	vnoj.updatetime,
	vnoj.countrytype,
	vnoj.`status`,
	vnoj.createtime,

IF (
	aaa.oneormore = 0,
	aaa.startdate,
	ccc.startdate
) AS 'startdate',

IF (
	mm.ismainproposer = 1,
	mm.chinesefullname,
	''
) AS 'mainporposer',

IF (
	vnoj.customerSource = 3,
	vcm.fullComName,
	qqq.fullComName
) AS 'fullComName',
 vnoj.errorMsg
FROM
	visa_new_order_jp vnoj
LEFT JOIN visa_new_customersource_jp vcm ON vnoj.id = vcm.order_jp_id
LEFT JOIN (
	SELECT
		*
	FROM
		(
			SELECT
				aa.ismainproposer,
				vnoj.id AS 'orderid',
				vncj.*, concat(
					vncj.chinesexingen,
					vncj.chinesenameen
				) AS 'fullnameen'
			FROM
				visa_new_customer_jp vncj
			LEFT JOIN visa_new_customer_order_jp vncoj ON vncj.id = vncoj.customer_jp_id
			LEFT JOIN visa_new_order_jp vnoj ON vnoj.id = vncoj.order_jp_id
			LEFT JOIN visa_new_proposer_info_jp aa ON aa.customer_jp_id = vncj.id
			ORDER BY
				aa.ismainproposer DESC,
				aa.relationproposer DESC,
				vncj.chinesefullname DESC
		) m
	WHERE
		m.id = (
			SELECT
				n.id
			FROM
				(
					SELECT
						vnoj.id AS 'orderid',
						vncj.id
					FROM
						visa_new_customer_jp vncj
					LEFT JOIN visa_new_customer_order_jp vncoj ON vncj.id = vncoj.customer_jp_id
					LEFT JOIN visa_new_order_jp vnoj ON vnoj.id = vncoj.order_jp_id
					LEFT JOIN visa_new_proposer_info_jp aa ON aa.customer_jp_id = vncj.id
					AND aa.order_jp_id = vnoj.id
					ORDER BY
						aa.ismainproposer DESC,
						aa.relationproposer DESC,
						vncj.chinesefullname DESC
				) n
			WHERE
				n.orderid = m.orderid
			GROUP BY
				n.orderid
			LIMIT 0,
			1
		)
) mm ON vnoj.id = mm.orderid
LEFT JOIN visa_new_comebaby_jp come ON come.id = vnoj.sendComId
LEFT JOIN visa_new_comebaby_jp_djs djs ON djs.id = vnoj.landComId
LEFT JOIN visa_employee eee ON eee.id = vnoj.operatePersonId
LEFT JOIN visa_new_trip_jp aaa ON aaa.order_jp_id = vnoj.id
LEFT JOIN visa_customer_management qqq ON qqq.id = vnoj.customer_manager_id
LEFT JOIN visa_new_company c ON c.id = vnoj.comId
LEFT JOIN (
	SELECT
		*, MIN(bbb.startdate)
	FROM
		visa_new_dateplan_jp bbb
	GROUP BY
		bbb.trip_jp_id
) ccc ON ccc.trip_jp_id = aaa.id
$condition

/*djsjptotal_list_data_new*/
SELECT
	vnoj.id,
	vnoj.comId,
	c.comType,
	c.comName,
	(
		SELECT
			COUNT(*)
		FROM
			visa_new_comebaby_jp p
    WHERE p.comId=vnoj.comId
	) AS sqscount,
	(
		SELECT
			COUNT(*)
		FROM
			visa_new_comebaby_jp_djs
	) AS djscount,
	vnoj.ordernumber,
	come.comFullName,
	vnoj.completedNumber,
	eee.fullName AS username,
	djs.landcomFullName,
	vnoj.headnum,
	vnoj.visatype,
	vnoj.senddate,
	vnoj.outdate,
	vnoj.updatetime,
	vnoj.countrytype,
	vnoj.`status`,
	vnoj.createtime,

IF (
	aaa.oneormore = 0,
	aaa.startdate,
	ccc.startdate
) AS 'startdate',

IF (
	mm.ismainproposer = 1,
	mm.chinesefullname,
	''
) AS 'mainporposer',

IF (
	vnoj.customerSource = 3,
	vcm.fullComName,
	qqq.fullComName
) AS 'fullComName',
 vnoj.errorMsg
FROM
	visa_new_order_jp vnoj
LEFT JOIN visa_new_customersource_jp vcm ON vnoj.id = vcm.order_jp_id
LEFT JOIN visa_new_comebaby_jp come ON come.id = vnoj.sendComId
LEFT JOIN visa_new_comebaby_jp_djs djs ON djs.id = vnoj.landComId
LEFT JOIN visa_employee eee ON eee.id = vnoj.operatePersonId
LEFT JOIN visa_new_trip_jp aaa ON aaa.order_jp_id = vnoj.id
LEFT JOIN visa_customer_management qqq ON qqq.id = vnoj.customer_manager_id
LEFT JOIN visa_new_company c ON c.id = vnoj.comId
LEFT JOIN (
	SELECT
		*, MIN(bbb.startdate)
	FROM
		visa_new_dateplan_jp bbb
	GROUP BY
		bbb.trip_jp_id
) ccc ON ccc.trip_jp_id = aaa.id
LEFT JOIN visa_new_customer_order_jp hhh ON vnoj.id = hhh.customer_jp_id
LEFT JOIN visa_new_customer_jp iii ON iii.id = hhh.customer_jp_id
LEFT JOIN (
	SELECT
		n.id,
		n.orderid,
		n.fullnameen,
		n.ismainproposer,
		n.chinesefullname
	FROM
		(
			SELECT
				vnoj.id AS 'orderid',
				vncj.id,
				aa.ismainproposer,
				vncj.chinesefullname,
				concat(
					vncj.chinesexingen,
					vncj.chinesenameen
				) AS 'fullnameen'
			FROM
				visa_new_customer_jp vncj
			LEFT JOIN visa_new_customer_order_jp vncoj ON vncj.id = vncoj.customer_jp_id
			LEFT JOIN visa_new_order_jp vnoj ON vnoj.id = vncoj.order_jp_id
			LEFT JOIN visa_new_proposer_info_jp aa ON aa.customer_jp_id = vncj.id
			AND aa.order_jp_id = vnoj.id
			ORDER BY
				aa.ismainproposer DESC,
				aa.relationproposer DESC,
				vncj.chinesefullname DESC
		) n
	GROUP BY
		n.orderid
) mm ON vnoj.id = mm.orderid
$condition



/*djsjptotal_1507-001_list_data*/
SELECT
	vnoj.id,
	vnoj.comId,
	c.comType,
		(
		SELECT
			COUNT(*)
		FROM
			visa_new_company where comType=1
	) AS sqscount,
	(
		SELECT
			COUNT(*)
		FROM
			visa_new_comebaby_jp_djs
	) AS djscount,
	vnoj.ordernumber,
	vnoj.sendComId,
	come.comFullName,
	vnoj.completedNumber,
	eee.fullName AS username,
	djs.landcomFullName,
	vnoj.landComId,
	vnoj.headnum,
	vnoj.visatype,
	vnoj.senddate,
	vnoj.outdate,
	vnoj.updatetime,
	vnoj.countrytype,
	vnoj.`status`,
	vnoj.createtime,

IF (
	aaa.oneormore = 0,
	aaa.startdate,
	ccc.startdate
) AS 'startdate',

IF (
	mm.ismainproposer = 1,
	mm.chinesefullname,
	''
) AS 'mainporposer',

IF (
	vnoj.customerSource = 3,
	vcm.fullComName,
	qqq.fullComName
) AS 'fullComName',
 vnoj.errorMsg
FROM
	visa_new_order_jp vnoj
LEFT JOIN visa_new_customersource_jp vcm ON vnoj.id = vcm.order_jp_id
LEFT JOIN visa_new_comebaby_jp come ON come.id = vnoj.sendComId
LEFT JOIN visa_new_comebaby_jp_djs djs ON djs.id = vnoj.landComId
LEFT JOIN visa_employee eee ON eee.id = vnoj.operatePersonId
LEFT JOIN visa_new_trip_jp aaa ON aaa.order_jp_id = vnoj.id
LEFT JOIN visa_customer_management qqq ON qqq.id = vnoj.customer_manager_id
LEFT JOIN visa_new_company c ON c.id = vnoj.comId
LEFT JOIN (
	SELECT
		*, MIN(bbb.startdate)
	FROM
		visa_new_dateplan_jp bbb
	GROUP BY
		bbb.trip_jp_id
) ccc ON ccc.trip_jp_id = aaa.id
LEFT JOIN visa_new_customer_order_jp hhh ON vnoj.id = hhh.customer_jp_id
LEFT JOIN visa_new_customer_jp iii ON iii.id = hhh.customer_jp_id
LEFT JOIN (
	SELECT
		n.id,
		n.orderid,
		n.fullnameen,
		n.ismainproposer,
		n.chinesefullname
	FROM
		(
			SELECT
				vnoj.id AS 'orderid',
				vncj.id,
				aa.ismainproposer,
				vncj.chinesefullname,
				concat(
					vncj.chinesexingen,
					vncj.chinesenameen
				) AS 'fullnameen'
			FROM
				visa_new_customer_jp vncj
			LEFT JOIN visa_new_customer_order_jp vncoj ON vncj.id = vncoj.customer_jp_id
			LEFT JOIN visa_new_order_jp vnoj ON vnoj.id = vncoj.order_jp_id
			LEFT JOIN visa_new_proposer_info_jp aa ON aa.customer_jp_id = vncj.id
			AND aa.order_jp_id = vnoj.id
			ORDER BY
				aa.ismainproposer DESC,
				aa.relationproposer DESC,
				vncj.chinesefullname DESC
		) n
	GROUP BY
		n.orderid
) mm ON vnoj.id = mm.orderid
$condition

/*djsjptotal_sqsbaby*/
SELECT
	vncj.*
FROM
	visa_new_order_jp vnoj
LEFT JOIN visa_new_comebaby_jp vncj ON vncj.id = vnoj.sendComId
LEFT JOIN visa_new_company c ON c.id = vnoj.comId
$condition
