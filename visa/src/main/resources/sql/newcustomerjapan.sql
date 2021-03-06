/*newcustomerjapan_list*/
select vncj.*,concat(vncj.chinesexingen,vncj.chinesenameen) as 'fullnameen' from visa_new_customer_jp vncj 
LEFT JOIN visa_new_customer_order_jp vncoj on vncj.id=vncoj.customer_jp_id
LEFT JOIN visa_new_order_jp vnoj on vnoj.id=vncoj.order_jp_id
LEFT JOIN visa_new_proposer_info_jp  aa on aa.customer_jp_id=vncj.id
where vnoj.id=@orderId
ORDER BY aa.relationproposer desc,aa.ismainproposer desc,vncj.chinesefullname desc
/*newcustomerjapan_getcustomerid*/
select vncj.* from visa_new_customer_jp vncj 
LEFT JOIN visa_new_customer_order_jp vncoj on vncj.id=vncoj.customer_jp_id
LEFT JOIN visa_new_order_jp vnoj on vnoj.id=vncoj.order_jp_id
LEFT JOIN visa_new_proposer_info_jp  aa on aa.customer_jp_id=vncj.id
where vncj.`status`=@status
ORDER BY aa.relationproposer desc,aa.ismainproposer desc,vncj.chinesefullname desc
/*newcustomerjapan_landlist*/
SELECT
vnoj.ordernumber,
vnoj.completedNumber,
eee.fullName,
vnoj.headnum,
vnoj.senddate,
vnoj.outdate,
vnoj.id,
vnoj.updatetime,
vnoj.countrytype,
vnoj.`status`,
vnoj.createtime,
vnoj.visatype,
IF (
aaa.oneormore = 0,
aaa.startdate,
ccc.startdate
) AS 'startdate',
If(
mm.ismainproposer=1,
mm.chinesefullname,
''
) as 'mainporposer',
If(
vnoj.customerSource=3,
vcm.fullComName,
qqq.fullComName
) as 'fullComName',
vnoj.errorMsg
FROM	visa_new_order_jp vnoj LEFT JOIN visa_new_customersource_jp vcm ON vnoj.id = vcm.order_jp_id
LEFT JOIN (select *
from (
select aa.ismainproposer,vnoj.id as 'orderid',vncj.*,concat(vncj.chinesexingen,vncj.chinesenameen) as 'fullnameen' from visa_new_customer_jp vncj
LEFT JOIN visa_new_customer_order_jp vncoj on vncj.id=vncoj.customer_jp_id
LEFT JOIN visa_new_order_jp vnoj on vnoj.id=vncoj.order_jp_id
LEFT JOIN visa_new_proposer_info_jp  aa on aa.customer_jp_id=vncj.id
ORDER BY aa.ismainproposer desc,aa.relationproposer desc,vncj.chinesefullname desc) m
where m.id = (
SELECT n.id from (
select vnoj.id as 'orderid',vncj.id from visa_new_customer_jp vncj
LEFT JOIN visa_new_customer_order_jp vncoj on vncj.id=vncoj.customer_jp_id
LEFT JOIN visa_new_order_jp vnoj on vnoj.id=vncoj.order_jp_id
LEFT JOIN visa_new_proposer_info_jp  aa on aa.customer_jp_id=vncj.id
ORDER BY aa.ismainproposer desc,aa.relationproposer desc,vncj.chinesefullname desc) n
where n.orderid =m.orderid
GROUP BY n.orderid
LIMIT 0,1
)) mm on vnoj.id=mm.orderid
LEFT JOIN visa_new_comebaby_jp come ON come.id  = vnoj.sendComId
LEFT JOIN visa_employee eee ON eee.id  = vnoj.operatePersonId
LEFT JOIN visa_new_trip_jp aaa ON aaa.order_jp_id = vnoj.id
LEFT JOIN visa_customer_management qqq on qqq.id=vnoj.customer_manager_id
LEFT JOIN (select *,MIN(bbb.startdate) from visa_new_dateplan_jp bbb
GROUP BY bbb.trip_jp_id ) ccc ON ccc.trip_jp_id = aaa.id
LEFT JOIN visa_new_customer_order_jp hhh on vnoj.id=hhh.customer_jp_id
LEFT JOIN visa_new_customer_jp iii on iii.id=hhh.customer_jp_id
$condition

/*newcustomerjapan_landlist_new*/

SELECT
	vnoj.ordernumber,
	vnoj.completedNumber,
	eee.fullName,
	vnoj.headnum,
	vnoj.senddate,
	vnoj.outdate,
	vnoj.id,
	vnoj.updatetime,
	vnoj.countrytype,
	vnoj.`status`,
	vnoj.createtime,
	vnoj.visatype,

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
LEFT JOIN visa_employee eee ON eee.id = vnoj.operatePersonId
LEFT JOIN visa_new_trip_jp aaa ON aaa.order_jp_id = vnoj.id
LEFT JOIN visa_customer_management qqq ON qqq.id = vnoj.customer_manager_id
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
				n.id,n.orderid,n.fullnameen,n.ismainproposer,n.chinesefullname
			FROM
				(
					SELECT
						vnoj.id AS 'orderid',
						vncj.id,aa.ismainproposer,	vncj.chinesefullname,
				concat(
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
				) n
GROUP BY n.orderid
) mm ON vnoj.id=mm.orderid
$condition