import ItemRow from "./ItemRow"

// Redux를 이용하면 해결된다.
function TodoList({ todoList, removeItem, updateItem }) {
    return (
        <div>
            <ol>
                {todoList.map((item, idx) => {
                    return (
                        <ItemRow
                            key={item.id}
                            item={item}
                            removeItem={removeItem}
                            updateItem={updateItem}
                        />
                    )
                })}
            </ol>
        </div>
    )
}

export default TodoList
