from VegetablePriceCrawler import *
from MySQLConnector import *

def insert_vegetable_data(ve, connector: MySQLConnector):
    # INSERT 쿼리
    insert_query = """
    INSERT INTO Product (
        name,
        date,
        price
    ) VALUES (%s, %s, %s)
    """

    values = get_query_data(ve)

    connector.executemany(insert_query, values)


def get_query_data(ve):
    values = []
    if type(ve) == type(dict()):
        for key, data_list in ve.items():
            for data in data_list:
                values.append((
                    data['kindname'],
                    data['date'],
                    data['price'],
                ))
        return values
    else:
        for data_list in ve:
            values.append((
                data_list['kindname'],
                data_list['date'],
                data_list['price'],
            ))
        return values


conn = MySQLConnector()
start_date = '2023-05-01'
end_date = '2023-05-09'

vegetableCrawler = VegetablePriceCrawler(start_date, end_date)
ve = vegetableCrawler.get_vegetables_data()
insert_vegetable_data(ve, conn)
