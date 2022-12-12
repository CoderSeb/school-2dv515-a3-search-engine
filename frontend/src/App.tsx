import { useState } from 'react'
import './App.css'

interface IResult {
  link: string,
  score: number,
  content: number,
  location: number,
  pageRank: number
}

enum SearchLevel {
  LOW = 'LOW',
  MEDIUM = 'MEDIUM',
  HIGH = 'HIGH'
}

function App() {
  const [isLoaded, setIsLoaded] = useState(true)
  const [error, setError] = useState(null)
  const [results, setResults] = useState<IResult[]>()
  const [searchLevel, setSearchLevel] = useState(SearchLevel.LOW)
  const [searchQuery, setSearchQuery] = useState('')



  // useEffect(() => {
  //   fetch(`http://localhost:8080/api/search?query=${searchQuery}&searchLevel=${searchLevel}`)
  //     .then(res => res.json())
  //     .then(
  //       (result: ICluster[]) => {
  //         setClusters(result)
  //         setIsLoaded(true)
  //       },
  //       (error) => {
  //         setIsLoaded(true)
  //         setError(error)
  //       }
  //     )
  // }, [iterations])

  const handleSearch = () => {
    console.log(searchQuery)
  }

  if (error) {
    return <div>Error: {error}</div>
  } else if (!isLoaded) {
    return <div>Loading...</div>
  } else {
    return (
      <div className="App">
        <h1>Assignment 3 - Search Engine</h1>
        <div className="searchContainer">
          <input className="searchInput" type="text" defaultValue={searchQuery} onChange={e => setSearchQuery(e.target.value)} placeholder="Search..." />
          <button className="searchButton" onClick={handleSearch}>Search!</button>
          <div className="searchResultContainer">

          </div>
        </div>
      </div>
    )
  }
}

export default App
