# Техасский холдем	
## Графика
само поле представляет собой стол зелёногог цвета с заданными координатами нижнего, левого, правого и верхнего игрока. Так же задаются координаты центральных карт, которые равномерно распределяются по остаткам поля в центре
дальше идёт зарисовка карт и трекера отображения денег.'''
Относительно координат игроков вычисляется положение карт, а так же сам трекер. Отрисовка карт идёт с картинки CardsWithGreen2.jpg. на столе в итоге разных 13 карт, 5 из которых общие. В зависимости от масти и номинала идёт отрисовка и вычисляются координаты нужной карты с нужной мастью.
для левого и правого игрока осуществляется поворот картинки для последующего определения нужных им карт для отрисовки.
Для трекера используюется в свою очередь png картинка с фишкой, внутрь которой врисован счётчик средств относительно центра этой фишки.
![image](https://github.com/user-attachments/assets/2505bbb2-d612-4b5e-af2a-e73c55218871)
## Реализация
В начале рандомно выбирается диллер, а от него, следовательно, small и big blind. Бот с помощью своей логики выбирает одно из дейтсвий, а игрок выбирает сам что делать нажатием на одну из кнопок.
![image](https://github.com/user-attachments/assets/86943fea-c8b5-4724-8a34-767f5b636cec)
когда ход дойдёт снова до этого игрока начнётся новый этап, например flop.
и так до последнего открытия карт, где определяется наивысшая комбинация, среди всех игроков, т.е. определяется победитель.
![image](https://github.com/user-attachments/assets/05e0c89c-25af-45a2-8582-a79e5515c590)
## Логика
Расчёт победных комбинаций основывается на двух списках: списков одинаковых карт и одинаковых мастей. На основе этих списков мы проходимся по комбинациям начиная с самой большей и правиряем на её наличие и при удачном исходе присваиваем её игроку.
## Боты
В каждый этап игры бот проводит расчёт какая на данный момент у него комбинация, а так же сколько у него аутов - т.е. карт, которые принесут ему выигрышную руку. В зависимости от руки, аутов и рандома бот принимает решение. 

# Структура пакетов
## graphics
### CardPack
Содержит изображения карт, жетонов, а так жк рубашек карт.
### DrawingClass
Класс, используемый в paintComponent в классе Viewport для более удобной ссылки на класс ImageWork.
### ImageWork
Класс со статическими методами для отрисовки самих карт, их разворота, просчёта координат для отрисовки нужной карты на картинке. Отрисовка рубашки, счётчика денег, отрисовка действий игроков и их центрирование относительно игроков, сделавших это действие.
## logic
### CardBase
Содержит всё, что нужно для обычных карт, т.е. саму карту, реализованную от неё игровую карту с параметрами для отрисовки, позиции для понимания положения карты, колоду, а так же игровые параметры с размерами поля, карт и др.
### ProccesGame
Самая важная часть. Содержит доступные игрокам действия, классы самих игроков, класс комбинаций, нужный игрокам, а так же класс, в котором хранятся все данные о данном этапе игры, а так же методы для перехода к следующему этапучто нужно для обычных карт, т.е. саму карту, реализованную от неё игровую карту с параметрами для отрисовки, позиции для понимания положения карты, колоду, а так же игровые параметры с размерами поля, карт и др.
### Trackers
Три класса Button - класс для реализации кнопки действия для игрока, ChipTracker - класс для хранения координат и использования фишки, как счётчика денег, а так же класс Buttons - для создания 3 кнопок действия Rise, Call и Fold.
### Класс Main
С самом мейне самым важным явлеются метод update, в котором и происходят основные действия игры. В нём игроки делают действия, вычисляется нужно ли переходит на новый этап.
