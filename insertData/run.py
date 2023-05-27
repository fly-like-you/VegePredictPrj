from VegetablePriceCrawler import *
from MySQLConnector import *
import json
import os
from datetime import date



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

def insert_predict_vegetlable_data(ve, connector: MySQLConnector):
    # INSERT 쿼리
    insert_query = """
    INSERT INTO Predict_Product (
        name,
        date,
        price
    ) VALUES (%s, %s, %s)
    """
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

start_date = str(os.environ.get("START_DATE"))
end_date = str(os.environ.get("END_DATE"))
print(start_date, end_date)
file_path = os.path.join("./insertData/", f"product_{start_date}.json")

conn = MySQLConnector()

vegetableCrawler = VegetablePriceCrawler(start_date, end_date)
ve = vegetableCrawler.get_vegetables_data()

json_string = json.dumps(ve)  # JSON 문자열로 변환
# print(ve)  # 출력: {"id": 1, "name": "Product Name", "price": 1000, "date": "2023-05-15"}

# 또는 파일로 저장
with open(file_path, "w") as file:
    json.dump(ve, file)


# 파이썬을 직접 데이터를 넣을 때 사용
# insert_vegetable_data(ve, conn)
